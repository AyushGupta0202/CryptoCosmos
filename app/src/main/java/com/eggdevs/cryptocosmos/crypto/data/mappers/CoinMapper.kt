package com.eggdevs.cryptocosmos.crypto.data.mappers

import com.eggdevs.cryptocosmos.crypto.data.networking.dto.CoinDto
import com.eggdevs.cryptocosmos.crypto.domain.Coin

fun CoinDto.toCoin(): Coin {
    return Coin(
        id = id,
        rank = rank,
        name = name,
        symbol = symbol,
        marketCapUsd = marketCapUsd,
        priceUsd = priceUsd,
        changePercent24Hr = changePercent24Hr
    )
}

fun List<CoinDto>.toCoins(): List<Coin> {
    return map { it.toCoin() }
}