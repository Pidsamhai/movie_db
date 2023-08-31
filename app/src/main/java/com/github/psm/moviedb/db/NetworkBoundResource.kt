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
) = flow {
    var cache = query().firstOrNull()

    if (shouldFetch(cache)) {
        emit(Resource.Loading)

        try {
            val res = fetch()
            saveFetchResult(res)
            cache = query().firstOrNull()
            if(cache != null) {
                emit(Resource.Success(cache))
            }
        } catch (e: Exception) {
            Timber.e(e)
            onFetchFailed(e)
            if(cache != null) {
                emit(Resource.Success(cache))
            } else {
                emit(Resource.Error(e))
            }
        }
    } else {
        if(cache != null) {
            emit(Resource.Success(cache))
        } else {
            emit(Resource.Error(Exception("Not Found")))
        }

    }
}