package com.eggdevs.cryptocosmos.crypto.data.networking

import com.eggdevs.cryptocosmos.BuildConfig
import com.eggdevs.cryptocosmos.core.data.networking.constructUrl
import com.eggdevs.cryptocosmos.core.data.networking.safeCall
import com.eggdevs.cryptocosmos.core.domain.util.NetworkError
import com.eggdevs.cryptocosmos.core.domain.util.Result
import com.eggdevs.cryptocosmos.core.domain.util.map
import com.eggdevs.cryptocosmos.crypto.data.mappers.toCoinPrices
import com.eggdevs.cryptocosmos.crypto.data.mappers.toCoins
import com.eggdevs.cryptocosmos.crypto.data.networking.dto.CoinsHistoryDto
import com.eggdevs.cryptocosmos.crypto.data.networking.dto.CoinsResponseDto
import com.eggdevs.cryptocosmos.crypto.domain.Coin
import com.eggdevs.cryptocosmos.crypto.domain.CoinPrice
import com.eggdevs.cryptocosmos.crypto.domain.RemoteCoinDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import java.time.ZoneId
import java.time.ZonedDateTime

class KtorRemoteCoinDataSource(
    private val httpClient: HttpClient
): RemoteCoinDataSource {

    override suspend fun getCoins(): Result<List<Coin>, NetworkError> {
        return safeCall<CoinsResponseDto> {
            httpClient.get(
                urlString = constructUrl("/assets"),
            ) {
                parameter("apiKey", BuildConfig.CC_API_KEY)
            }
        }.map { response ->
            response.data.toCoins()
        }
    }

    override suspend fun getCoinHistory(
        coinId: String,
        start: ZonedDateTime,
        end: ZonedDateTime
    ): Result<List<CoinPrice>, NetworkError> {
        val startMillis = start
            .withZoneSameInstant(ZoneId.of("UTC"))
            .toInstant()
            .toEpochMilli()
        val endMillis = end
            .withZoneSameInstant(ZoneId.of("UTC"))
            .toInstant()
            .toEpochMilli()
        return safeCall<CoinsHistoryDto> {
            httpClient.get(
                urlString = constructUrl("/assets/$coinId/history")
            ) {
                parameter("apiKey", BuildConfig.CC_API_KEY)
                parameter("interval", "h6")
                parameter("start", startMillis)
                parameter("end", endMillis)
            }
        }.map { response ->
            response.data.toCoinPrices()
        }
    }
}