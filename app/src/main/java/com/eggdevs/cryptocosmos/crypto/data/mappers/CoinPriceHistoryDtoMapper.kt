package com.eggdevs.cryptocosmos.crypto.data.mappers

import com.eggdevs.cryptocosmos.crypto.data.networking.dto.CoinPriceDto
import com.eggdevs.cryptocosmos.crypto.domain.CoinPrice
import java.time.Instant
import java.time.ZoneId

fun CoinPriceDto.toCoinPrice(): CoinPrice {
    return CoinPrice(
        priceUsd = priceUsd,
        dateTime = Instant
            .ofEpochMilli(time)
            .atZone(ZoneId.systemDefault())
    )
}

fun List<CoinPriceDto>.toCoinPrices(): List<CoinPrice> {
    return map { it.toCoinPrice() }
}