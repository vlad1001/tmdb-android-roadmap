package com.example.moviestmdb.core.util

import com.example.moviestmdb.core.result.Result

/**
 * Wrap a suspending API [call] in try/catch. In case an exception is thrown.
 */
suspend fun <T : Any> safeApiCall(call: suspend () -> Result<T>): Result<T> {
    return try {
        call.invoke()
    } catch (t: Throwable) {
        Result.Error(t)
    }
}