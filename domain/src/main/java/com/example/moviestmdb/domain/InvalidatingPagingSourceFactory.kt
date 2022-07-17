package com.example.moviestmdb.domain

import androidx.paging.PagingSource

class InvalidatingPagingSourceFactory<Key : Any, Value : Any>(
    private val createPagingSourceCallback: () -> PagingSource<Key, Value>
) : () -> PagingSource<Key, Value> {
    private val pagingSources = mutableListOf<PagingSource<Key, Value>>()

    override fun invoke(): PagingSource<Key, Value> =
        createPagingSourceCallback.invoke().also { pagingSources.add(it) }

    fun invalidate() {
        pagingSources.toList().forEach { pagingSource ->
            if (!pagingSource.invalid) {
                pagingSource.invalidate()
            }
        }

        pagingSources.removeAll { it.invalid }
    }
}