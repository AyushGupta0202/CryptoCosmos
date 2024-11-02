package com.eggdevs.cryptocosmos

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.eggdevs.cryptocosmos.core.presentation.util.ObserveAsEvents
import com.eggdevs.cryptocosmos.core.presentation.util.toString
import com.eggdevs.cryptocosmos.crypto.presentation.coin_list.CoinListEvent
import com.eggdevs.cryptocosmos.crypto.presentation.coin_list.CoinListScreen
import com.eggdevs.cryptocosmos.crypto.presentation.coin_list.CoinListViewModel
import com.eggdevs.cryptocosmos.crypto.presentation.theme.CryptoCosmosTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val coinListViewModel = koinViewModel<CoinListViewModel>()
            CryptoCosmosTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
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
                    CoinListScreen(
                        modifier = Modifier.padding(innerPadding),
                        state = state
                    )
                }
            }
        }
    }
}