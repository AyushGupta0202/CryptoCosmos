package com.eggdevs.cryptocosmos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.eggdevs.cryptocosmos.core.navigation.AdaptiveCoinListDetailPane
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
                    AdaptiveCoinListDetailPane(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}