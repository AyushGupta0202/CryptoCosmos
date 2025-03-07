package com.eggdevs.cryptocosmos.crypto.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eggdevs.cryptocosmos.core.domain.util.onError
import com.eggdevs.cryptocosmos.core.domain.util.onSuccess
import com.eggdevs.cryptocosmos.crypto.domain.RemoteCoinDataSource
import com.eggdevs.cryptocosmos.crypto.presentation.coin_detail.DataPoint
import com.eggdevs.cryptocosmos.crypto.presentation.models.CoinUi
import com.eggdevs.cryptocosmos.crypto.presentation.models.toCoinUis
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class CoinListViewModel(
    private val remoteCoinDataSource: RemoteCoinDataSource
): ViewModel() {

    private val _state = MutableStateFlow(CoinListState())
    var state = _state
        .onStart {
            loadCoins()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            CoinListState()
        )

    private val _coinListEvents = Channel<CoinListEvent>()
    val coinListEvents = _coinListEvents.receiveAsFlow()

    fun onAction(action: CoinListAction) {
        when(action) {
            is CoinListAction.OnCoinClick -> {
                selectCoin(action.coinUi)
            }
            CoinListAction.OnRefresh -> {
                loadCoins()
            }
        }
    }

    private fun loadCoins() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            remoteCoinDataSource
                .getCoins()
                .onSuccess { coins ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            coins = coins.toCoinUis()
                        )
                    }
                }
                .onError { error ->
                    _state.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                    _coinListEvents.send(CoinListEvent.Error(error))
                }
        }
    }

    private fun selectCoin(coinUi: CoinUi) {
        _state.update {
            it.copy(
                selectedCoin = coinUi
            )
        }
        viewModelScope.launch {
            remoteCoinDataSource
                .getCoinHistory(
                    coinId = coinUi.id,
                    start = ZonedDateTime.now().minusDays(5),
                    end = ZonedDateTime.now()
                )
                .onSuccess { history ->
                    val dataPoints = history
                        .sortedBy { it.dateTime }
                        .map {
                            DataPoint(
                                x = it.dateTime.hour.toFloat(),
                                y = it.priceUsd.toFloat(),
                                xLabel = DateTimeFormatter
                                    .ofPattern("ha\nM/d")
                                    .format(it.dateTime)
                            )
                        }

                    _state.update {
                        it.copy(
                            selectedCoin = it.selectedCoin?.copy(
                                coinPriceHistory = dataPoints
                            )
                        )
                    }
                }
                .onError {
                    _coinListEvents.send(CoinListEvent.Error(it))
                }
        }
    }
}