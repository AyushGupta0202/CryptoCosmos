package com.eggdevs.cryptocosmos.core.presentation.util

import android.content.Context
import com.eggdevs.cryptocosmos.R
import com.eggdevs.cryptocosmos.core.domain.util.NetworkError

fun NetworkError.toString(context: Context): String {
    val resId = when(this) {
        NetworkError.TOO_MANY_REQUESTS -> R.string.error_request_timeout
        NetworkError.REQUEST_TIMED_OUT -> R.string.error_too_many_request
        NetworkError.NO_INTERNET -> R.string.error_no_internet
        NetworkError.SERVER_ERROR -> R.string.error_unknown
        NetworkError.SERIALIZATION_ERROR -> R.string.error_serialization
        NetworkError.UNKNOWN -> R.string.error_unknown
    }
    return context.getString(resId)
}