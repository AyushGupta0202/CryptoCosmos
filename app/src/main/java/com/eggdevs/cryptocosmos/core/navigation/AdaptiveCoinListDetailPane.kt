@file:OptIn(ExperimentalMaterial3AdaptiveApi::class)

package com.eggdevs.cryptocosmos.core.navigation

import android.widget.Toast
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.eggdevs.cryptocosmos.core.presentation.util.ObserveAsEvents
import com.eggdevs.cryptocosmos.core.presentation.util.toString
import com.eggdevs.cryptocosmos.crypto.presentation.coin_detail.CoinDetailScreen
import com.eggdevs.cryptocosmos.crypto.presentation.coin_list.CoinListAction
import com.eggdevs.cryptocosmos.crypto.presentation.coin_list.CoinListEvent
import com.eggdevs.cryptocosmos.crypto.presentation.coin_list.CoinListScreen
import com.eggdevs.cryptocosmos.crypto.presentation.coin_list.CoinListViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AdaptiveCoinListDetailPane(
    modifier: Modifier = Modifier,
    coinListViewModel: CoinListViewModel = koinViewModel()
) {
    val state by coinListViewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    ObserveAsEvents(events = coinListViewModel.coinListEvents) { event ->
        when (event) {
            is CoinListEvent.Error -> {
                Toast.makeText(
                    context,
                    event.error.toString(context),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()
    NavigableListDetailPaneScaffold(
        navigator = navigator,
        listPane = {
            AnimatedPane {
                CoinListScreen(
                    state = state,
                    onAction = { action ->
                        coinListViewModel.onAction(action)
                        when(action) {
                            is CoinListAction.OnCoinClick -> {
                                navigator.navigateTo(
                                    pane = ListDetailPaneScaffoldRole.Detail
                                )
                            }
                            else -> Unit
                        }
                    }
                )
            }
        },
        detailPane = {
            AnimatedPane {
                CoinDetailScreen(
                    state = state
                )
            }
        },
        modifier = modifier
    )
}