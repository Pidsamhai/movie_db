package com.github.psm.movie.review.repository

import com.github.psm.movie.review.db.BoxStore
import com.github.psm.movie.review.db.model.BaseResponse
import com.github.psm.movie.review.db.model.detail.MovieDetail
import com.github.psm.movie.review.db.model.genre.GenreResponse
import com.github.psm.movie.review.db.model.upcoming.UpComingResponse
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class TMDBRepositoryImpl @Inject constructor(
    private val boxStore: BoxStore,
    private val apiServices: HttpClient
) : TMDBRepository {

    override suspend fun getPopular() {
        try {
            val result = apiServices.get<BaseResponse>(path = POPULAR_ROUTE) {
                parameter("page", 1)
            }
            result.movies?.let { boxStore.movie.put(it) }
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    override suspend fun getMovieDetail(movieId: Int) {
        try {
            val result = apiServices.get<MovieDetail>(path = "${MOVIE_DETAIL_ROUTE}/$movieId")
            boxStore.movieDetail.put(result)
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    override fun getGenres(): Flow<GenreResponse> = flow {
        val result = apiServices.get<GenreResponse>(path = GENRES_ROUTE)
        emit(result)
    }

    override suspend fun getGenreNormal() {
        try {
            val result = apiServices.get<GenreResponse>(path = GENRES_ROUTE)
            result.genres?.let { boxStore.genre.put(it) }
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    override fun search(keyWord: String, page: Int): Flow<BaseResponse> = flow {
        try {
            val result = apiServices.get<BaseResponse>(path = SEARCH_ROUTE) {
                parameter("query", keyWord)
                parameter("page", page)
                parameter("include_adult", true)
            }
            emit(result)
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    override fun getUpComing(): Flow<UpComingResponse> = flow {
        try {
            val result = apiServices.get<UpComingResponse>(path = UPCOMING_ROUTE) {
                parameter("page", 1)
                parameter("region", "US")
            }
            emit(result)
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    companion object {
        private const val POPULAR_ROUTE = "/movie/popular"
        private const val MOVIE_DETAIL_ROUTE = "/movie"
        private const val GENRES_ROUTE = "/genre/movie/list"
        private const val SEARCH_ROUTE = "/search/movie"
        private const val UPCOMING_ROUTE = "/movie/upcoming"
    }
}