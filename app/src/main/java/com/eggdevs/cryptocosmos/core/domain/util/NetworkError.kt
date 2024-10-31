package com.eggdevs.cryptocosmos.core.domain.util

enum class NetworkError: Error {
    TOO_MANY_REQUESTS,
    REQUEST_TIMED_OUT,
    NO_INTERNET,
    SERVER_ERROR,
    SERIALIZATION_ERROR,
    UNKNOWN
}