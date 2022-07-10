package com.example.moviestmdb.core.util

import com.example.moviestmdb.domain.InvokeError
import com.example.moviestmdb.domain.InvokeStarted
import com.example.moviestmdb.domain.InvokeStatus
import com.example.moviestmdb.domain.InvokeSuccess
import kotlinx.coroutines.flow.*
import timber.log.Timber
import java.util.concurrent.atomic.AtomicInteger
import  com.example.moviestmdb.core.result.*

class ObservableLoadingCounter {
    private val count = AtomicInteger()
    private val loadingState = MutableStateFlow(count.get())

    val observable: Flow<Boolean>
        get() = loadingState.map { it > 0 }.distinctUntilChanged()

    fun addLoader() {
        loadingState.value = count.incrementAndGet()
    }

    fun removeLoader() {
        loadingState.value = count.decrementAndGet()
    }
}

@JvmName("collectStatusR")
suspend fun <T> Flow<Result<T>>.collectStatus(
    counter: ObservableLoadingCounter,
    uiMessageManager: UiMessageManager? = null,
) = onStart { counter.addLoader() }.collect { result ->
    when (result) {
        is Result.Success -> counter.removeLoader()
        is Result.Error -> {
            uiMessageManager?.emitMessage(UiMessage(result.exception))
            counter.removeLoader()
        }
    }
}