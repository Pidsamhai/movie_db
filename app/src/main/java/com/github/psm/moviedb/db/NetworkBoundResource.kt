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
    crossinline shouldFetch: (ResultType?) -> Boolean = { true },
    crossinline formatResponse: (RequestType?) -> ResultType? = { null }
) = flow {
    val data = query()

    if (shouldFetch(data.firstOrNull())) {
        emit(Resource.Loading)

        try {
            val res = fetch()
            Timber.i("ResX %s", res)
            saveFetchResult(res)
            var cache = query().firstOrNull()
            Timber.i("Cache After Save %s", cache)
            if(cache != null) {
                emit(Resource.Success(cache))
            }
//            emit(cache)
//            emitAll(query().mapLatest { Resource.Success(it) })
        } catch (e: Exception) {
            Timber.e(e)
            onFetchFailed(e)
            emitAll(query().mapLatest { Resource.Error(e, it) })
        }
    } else {
        Timber.i("Using Cache")
        emitAll(data.mapLatest { Resource.Success(it) })
    }
}