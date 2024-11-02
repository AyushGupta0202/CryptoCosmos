package com.eggdevs.cryptocosmos.crypto.presentation.coin_list

import com.eggdevs.cryptocosmos.crypto.presentation.models.CoinUi

sealed interface CoinListAction {
    data class OnCoinClick(val coinUi: CoinUi): CoinListAction
    data object OnRefresh: CoinListAction
}