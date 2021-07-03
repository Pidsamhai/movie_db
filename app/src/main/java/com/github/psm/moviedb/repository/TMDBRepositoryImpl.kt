package com.github.psm.moviedb.repository

import com.github.psm.moviedb.db.BoxStore
import com.github.psm.moviedb.db.Response
import com.github.psm.moviedb.db.model.BaseResponse
import com.github.psm.moviedb.db.model.detail.MovieDetail
import com.github.psm.moviedb.db.model.genre.GenreResponse
import com.github.psm.moviedb.db.model.movie.credit.MovieCredit
import com.github.psm.moviedb.db.model.person.Person
import com.github.psm.moviedb.db.model.person.movie.PersonMovieCredit
import com.github.psm.moviedb.db.model.person.tv.PersonTvCredit
import com.github.psm.moviedb.db.model.upcoming.UpComingResponse
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

    override suspend fun getPopular(page: Int): Response<BaseResponse> {
        return try {
            val result = apiServices.get<BaseResponse>(path = POPULAR_ROUTE) {
                parameter("page", page)
            }
            result.movies?.let { boxStore.movie.put(it) }
            Response.Success(result)
        } catch (e: Exception) {
            Timber.e(e)
            Response.Error(e)
        }
    }

    override suspend fun getMovieDetail(movieId: Long) {
        try {
            val result = apiServices.get<MovieDetail>(path = "$MOVIE_DETAIL_ROUTE/$movieId")
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

    override fun getUpComingFlow(): Flow<UpComingResponse> = flow {
        try {
            val result = getUpComing(1)
            if (result is Response.Success) emit(result.value)
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    override suspend fun getUpComing(page: Int): Response<UpComingResponse> {
        return try {
            val result = apiServices.get<UpComingResponse>(path = UPCOMING_ROUTE) {
                parameter("page", page)
                parameter("region", "US")
            }
            Response.Success(result)
        } catch (e: Exception) {
            Timber.e(e)
            Response.Error(e)
        }
    }

    override suspend fun getMovieCredit(movieId: Long) {
        try {
            val result = apiServices.get<MovieCredit>(path = MOVIE_CREDIT_ROUTE.format(movieId)) {
                parameter("region", "US")
            }
            Timber.i(result.toString())
            boxStore.movieCredit.put(result)
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    override suspend fun getPersonDetail(personId: Long) {
        try {
            val result = apiServices.get<Person>(path = PERSON.format(personId))
            Timber.i(result.toString())
            boxStore.person.put(result)
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    override suspend fun getPersonTvCredit(personId: Long) {
        try {
            val result = apiServices.get<PersonTvCredit>(path = PERSON_TV_CREDIT_ROUTE.format(personId))
            Timber.i(result.toString())
            boxStore.personTvCredit.put(result)
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    override suspend fun getPersonMovieCredit(personId: Long) {
        try {
            val result = apiServices.get<PersonMovieCredit>(path = PERSON_MOVIE_CREDIT_ROUTE.format(personId))
            Timber.i(result.toString())
            boxStore.personMovieCredit.put(result)
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
        private const val MOVIE_CREDIT_ROUTE = "/movie/%s/credits"
        private const val PERSON = "/person/%s"
        private const val PERSON_MOVIE_CREDIT_ROUTE = "/person/%s/movie_credits"
        private const val PERSON_TV_CREDIT_ROUTE = "/person/%s/tv_credits"
    }
}