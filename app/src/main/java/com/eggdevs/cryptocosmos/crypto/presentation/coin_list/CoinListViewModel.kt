package com.eggdevs.cryptocosmos.crypto.presentation.coin_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CoinListViewModel: ViewModel() {

    var state by mutableStateOf(CoinListState())


}