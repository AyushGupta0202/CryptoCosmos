package com.eggdevs.cryptocosmos.core.data.networking

import com.eggdevs.cryptocosmos.core.domain.util.NetworkError
import com.eggdevs.cryptocosmos.core.domain.util.Result
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import kotlin.coroutines.coroutineContext

suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
): Result<T, NetworkError> {
    val response = try {
        execute()
    } catch (e: UnresolvedAddressException) {
        return Result.Error(NetworkError.NO_INTERNET)
    } catch (e: SerializationException) {
        return Result.Error(NetworkError.SERIALIZATION_ERROR)
    } catch (e: Exception) {
        coroutineContext.ensureActive() // rethrows the cancellation exception thrown by coroutines cancellation
        return Result.Error(NetworkError.UNKNOWN)
    }
    return responseToResult(response)
}