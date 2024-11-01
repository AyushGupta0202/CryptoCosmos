package com.eggdevs.cryptocosmos.crypto.domain

import com.eggdevs.cryptocosmos.core.domain.util.NetworkError
import com.eggdevs.cryptocosmos.core.domain.util.Result

interface RemoteCoinDataSource {
    suspend fun getCoins(): Result<List<Coin>, NetworkError>
}