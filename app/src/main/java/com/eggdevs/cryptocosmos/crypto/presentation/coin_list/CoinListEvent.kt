package com.eggdevs.cryptocosmos.crypto.presentation.coin_list

import com.eggdevs.cryptocosmos.core.domain.util.NetworkError

sealed interface CoinListEvent {
    data class Error(val error: NetworkError): CoinListEvent
}