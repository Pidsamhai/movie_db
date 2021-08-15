package com.github.psm.moviedb.db

import kotlinx.coroutines.flow.*
import timber.log.Timber

/**
 * https://stackoverflow.com/questions/58486364/networkboundresource-with-kotlin-coroutines
 */
inline fun <ResultType: Any, RequestType : Any> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline onFetchFailed: (Exception) -> Unit = {  },
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = flow {
    val data = query()

    val flow = if (shouldFetch(data.first())) {
        emit(Resource.Loading)

        try {
            saveFetchResult(fetch())
            query().map { Resource.Success(it) }
        } catch (e: Exception) {
            Timber.e(e)
            onFetchFailed(e)
            query().map { Resource.Error(e, it) }
        }
    } else {
        data.map { Resource.Success(it) }
    }

    emitAll(flow)
}