package com.eggdevs.cryptocosmos.crypto.data.networking

import com.eggdevs.cryptocosmos.core.data.networking.constructUrl
import com.eggdevs.cryptocosmos.core.data.networking.safeCall
import com.eggdevs.cryptocosmos.core.domain.util.NetworkError
import com.eggdevs.cryptocosmos.core.domain.util.Result
import com.eggdevs.cryptocosmos.core.domain.util.map
import com.eggdevs.cryptocosmos.crypto.data.mappers.toCoins
import com.eggdevs.cryptocosmos.crypto.data.networking.dto.CoinsResponseDto
import com.eggdevs.cryptocosmos.crypto.domain.Coin
import com.eggdevs.cryptocosmos.crypto.domain.RemoteCoinDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class KtorRemoteCoinDataSource(
    private val httpClient: HttpClient
): RemoteCoinDataSource {

    override suspend fun getCoins(): Result<List<Coin>, NetworkError> {
        return safeCall<CoinsResponseDto> {
            httpClient.get(
                urlString = constructUrl("/assets"),
            )
        }.map { response ->
            response.data.toCoins()
        }
    }
}