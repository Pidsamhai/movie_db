package com.github.psm.moviedb.ui.upcoming

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.github.psm.moviedb.db.Response
import com.github.psm.moviedb.db.model.Movie
import com.github.psm.moviedb.repository.TMDBRepository
import timber.log.Timber
import javax.inject.Inject

class UpComingDataSource @Inject constructor(
    private val repository: TMDBRepository
): PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { state.closestPageToPosition(it)?.nextKey }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val key = params.key ?: 1
            when (val res = repository.getUpComing(key)) {
                is Response.Error<*> -> throw res.e
                is Response.Success -> {
                    LoadResult.Page(
                        data = res.value.movies ?: listOf(),
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