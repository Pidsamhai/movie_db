package com.github.psm.moviedb.ui.search

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.github.psm.moviedb.db.Response
import com.github.psm.moviedb.db.model.BaseResponse
import timber.log.Timber

inline fun <R : Any> pagingDataSource(
    crossinline fetch: suspend (page: Int) -> Response<BaseResponse<R>>,
): PagingSource<Int, R> {
    return object :  PagingSource<Int, R>() {
        override fun getRefreshKey(state: PagingState<Int, R>): Int? {
            return state.anchorPosition?.let { state.closestPageToPosition(it)?.nextKey }
        }

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, R> {
            return try {
                val key = params.key ?: 1
                when (val res = fetch(key)) {
                    is Response.Error<*> -> throw res.e
                    is Response.Success -> {
                        LoadResult.Page(
                            data = res.value.results ?: listOf(),
                            nextKey = if (key == res.value.totalPages) null else res.value.page?.plus(1),
                            prevKey = if (key == 1) null else key.minus(1)
                        )
                    }
                }
            } catch (e: Exception) {
                Timber.e(e)
                LoadResult.Error(e)
            }
        }
    }
}