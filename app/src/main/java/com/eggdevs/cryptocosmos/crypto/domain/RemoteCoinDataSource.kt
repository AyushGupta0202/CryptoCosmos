package com.eggdevs.cryptocosmos.crypto.domain

import com.eggdevs.cryptocosmos.core.domain.util.NetworkError
import com.eggdevs.cryptocosmos.core.domain.util.Result
import java.time.ZonedDateTime

interface RemoteCoinDataSource {
    suspend fun getCoins(): Result<List<Coin>, NetworkError>
    suspend fun getCoinHistory(
        coinId: String,
        start: ZonedDateTime,
        end: ZonedDateTime
    ): Result<List<CoinPrice>, NetworkError>
}