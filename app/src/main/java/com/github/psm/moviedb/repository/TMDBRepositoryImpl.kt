package com.github.psm.moviedb.repository

import com.github.psm.moviedb.db.BoxStore
import com.github.psm.moviedb.db.Resource
import com.github.psm.moviedb.db.Response
import com.github.psm.moviedb.db.model.Movie
import com.github.psm.moviedb.db.model.MovieResponse
import com.github.psm.moviedb.db.model.Movie_
import com.github.psm.moviedb.db.model.TvResponse
import com.github.psm.moviedb.db.model.detail.MovieDetail
import com.github.psm.moviedb.db.model.detail.MovieDetail_
import com.github.psm.moviedb.db.model.genre.GenreResponse
import com.github.psm.moviedb.db.model.movie.credit.MovieCredit
import com.github.psm.moviedb.db.model.movie.credit.MovieCredit_
import com.github.psm.moviedb.db.model.person.Person
import com.github.psm.moviedb.db.model.person.movie.PersonMovieCredit
import com.github.psm.moviedb.db.model.person.tv.PersonTvCredit
import com.github.psm.moviedb.db.model.tv.credits.TvCredit
import com.github.psm.moviedb.db.model.tv.credits.TvCredit_
import com.github.psm.moviedb.db.model.tv.detail.TvDetail
import com.github.psm.moviedb.db.model.tv.detail.TvDetail_
import com.github.psm.moviedb.db.model.tv.popular.Tv
import com.github.psm.moviedb.db.model.tv.popular.TvPopularResponse
import com.github.psm.moviedb.db.model.tv.popular.Tv_
import com.github.psm.moviedb.db.model.upcoming.UpComingResponse
import com.github.psm.moviedb.db.networkBoundResource
import io.ktor.client.*
import io.ktor.client.request.*
import io.objectbox.kotlin.flow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class TMDBRepositoryImpl @Inject constructor(
    private val boxStore: BoxStore,
    private val apiServices: HttpClient
) : TMDBRepository {

    override fun getPopularMovieFlow(page: Int, refresh: Boolean): Flow<Resource<List<Movie>>> =
        networkBoundResource(
            query = {
                boxStore.movie
                    .query()
                    .orderDesc(Movie_.popularity)
                    .orderDesc(Movie_.releaseDate)
                    .orderDesc(Movie_.voteCount)
                    .orderDesc(Movie_.voteAverage)
                    .build()
                    .flow()
                    .map {
                        it.take(LIMIT_LIST_ITEM)
                    }
            },
            fetch = {
                apiServices.get<MovieResponse>(path = POPULAR_MOVIE_ROUTE) {
                    parameter("page", page)
                }
            },
            saveFetchResult = { boxStore.movie.put(it.results) },
            shouldFetch = { refresh }
        ).flowOn(Dispatchers.IO).distinctUntilChanged()

    override suspend fun getPopularMovie(page: Int): Response<MovieResponse> {
        return try {
            val result = apiServices.get<MovieResponse>(path = POPULAR_MOVIE_ROUTE) {
                parameter("page", page)
            }
            Response.Success(result)
        } catch (e: Exception) {
            Timber.e(e)
            Response.Error(e)
        }
    }

    override fun getMovieDetail(id: Long): Flow<Resource<MovieDetail>> = networkBoundResource(
        query = { flow {  boxStore.movieDetail.query()
            .equal(MovieDetail_.id, id)
            .build()
            .findFirst()?.let {
                emit(it)
            } }},
        fetch = { apiServices.get<MovieDetail>(path = "$MOVIE_DETAIL_ROUTE/$id") },
        saveFetchResult = {boxStore.movieDetail.put(it)},
        formatResponse = { it }
    )

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

    override suspend fun searchMovie(keyWord: String, page: Int): Response<MovieResponse> {
        return try {
            val result = apiServices.get<MovieResponse>(path = SEARCH_MOVIE_ROUTE) {
                parameter("query", keyWord)
                parameter("page", page)
                parameter("include_adult", true)
            }
            Response.Success(result)
        } catch (e: Exception) {
            Timber.e(e)
            Response.Error(e)
        }
    }

    override fun getUpcomingMovieFlow(): Flow<UpComingResponse> = flow {
        try {
            val result = getUpcomingMovie(1)
            if (result is Response.Success) emit(result.value)
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    override suspend fun getUpcomingMovie(page: Int): Response<UpComingResponse> {
        return try {
            val result = apiServices.get<UpComingResponse>(path = UPCOMING_MOVIE_ROUTE) {
                parameter("page", page)
                parameter("region", "US")
            }
            Response.Success(result)
        } catch (e: Exception) {
            Timber.e(e)
            Response.Error(e)
        }
    }

    override fun getMovieCredit(id: Long): Flow<Resource<MovieCredit>> = networkBoundResource(
        query = { flow {
            boxStore.movieCredit.query().equal(MovieCredit_.id, id).build().findFirst()?.let {
                emit(it)
            }
        } },
        fetch = {
                try {
                    val res =  apiServices.get<MovieCredit>(path = MOVIE_CREDIT_ROUTE.format(id))
                    return@networkBoundResource  res
                } catch (e: Exception) {
                    Timber.d("Fetch Error %s", id)
                    Timber.e(e)
                    throw e
                }
        },
        saveFetchResult = { boxStore.movieCredit.put(it) }
    )

    override suspend fun getPersonDetail(personId: Long) {
        try {
            val result = apiServices.get<Person>(path = PERSON_DETAIL_ROUTE.format(personId))
            Timber.i(result.toString())
            boxStore.person.put(result)
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    override suspend fun getPersonTvCredit(personId: Long) {
        try {
            val result =
                apiServices.get<PersonTvCredit>(path = PERSON_TV_CREDIT_ROUTE.format(personId))
            Timber.i(result.toString())
            boxStore.personTvCredit.put(result)
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    override suspend fun getPersonMovieCredit(personId: Long) {
        try {
            val result =
                apiServices.get<PersonMovieCredit>(path = PERSON_MOVIE_CREDIT_ROUTE.format(personId))
            Timber.i(result.toString())
            boxStore.personMovieCredit.put(result)
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    override suspend fun getPopularTv(page: Int): Response<TvPopularResponse> {
        return try {
            val result = apiServices.get<TvPopularResponse>(path = POPULAR_TV_ROUTE) {
                parameter("page", page)
            }
            result.tvs?.let { boxStore.tv.put(it) }
            Response.Success(result)
        } catch (e: Exception) {
            Timber.e(e)
            Response.Error(e)
        }
    }

    override fun getPopularTvFlow(page: Int, refresh: Boolean): Flow<Resource<List<Tv>>> =
        networkBoundResource(
            query = {
                boxStore.tv
                    .query()
                    .orderDesc(Tv_.popularity)
                    .orderDesc(Tv_.firstAirDate)
                    .orderDesc(Tv_.voteCount)
                    .orderDesc(Tv_.voteAverage)
                    .build()
                    .flow()
                    .map {
                        it.take(LIMIT_LIST_ITEM)
                    }
            },
            fetch = {
                apiServices.get<TvPopularResponse>(path = POPULAR_TV_ROUTE) {
                    parameter("page", page)
                }
            },
            saveFetchResult = { boxStore.tv.put(it.tvs) },
            shouldFetch = { refresh }
        ).flowOn(Dispatchers.IO).distinctUntilChanged()

    override suspend fun searchTv(keyWord: String, page: Int): Response<TvResponse> {
        return try {
            val result = apiServices.get<TvResponse>(path = SEARCH_TV_ROUTE) {
                parameter("query", keyWord)
                parameter("page", page)
                parameter("include_adult", true)
            }
            Response.Success(result)
        } catch (e: Exception) {
            Timber.e(e)
            Response.Error(e)
        }
    }

    override fun getTvDetail(id: Long): Flow<Resource<TvDetail>> = networkBoundResource(
        query = { flow { boxStore.tvDetail.query().equal(TvDetail_.id, id).build().findFirst()?.let {
            emit(it)
        } } },
        fetch = { apiServices.get<TvDetail>(path = TV_DETAIL_ROUTE.format(id)) },
        saveFetchResult = {
            Timber.i("On Save")
            boxStore.tvDetail.put(it)
        },
        formatResponse = {
            return@networkBoundResource it
        }
    )

    override fun getTvCredit(id: Long): Flow<Resource<TvCredit>> = networkBoundResource(
        query = { flow { boxStore.tvCredit.query().equal(TvCredit_.id, id).build().findFirst()?.let {
            emit(it)
        } } },
        fetch = { apiServices.get<TvCredit>(path = TV_CREDIT_ROUTE.format(id)) },
        saveFetchResult = { boxStore.tvCredit.put(it) }
    )

    companion object {
        private const val POPULAR_MOVIE_ROUTE = "/movie/popular"
        private const val MOVIE_DETAIL_ROUTE = "/movie"
        private const val GENRES_ROUTE = "/genre/movie/list"
        private const val SEARCH_MOVIE_ROUTE = "/search/movie"
        private const val UPCOMING_MOVIE_ROUTE = "/movie/upcoming"
        private const val MOVIE_CREDIT_ROUTE = "/movie/%s/credits"
        private const val PERSON_DETAIL_ROUTE = "/person/%s"
        private const val PERSON_MOVIE_CREDIT_ROUTE = "/person/%s/movie_credits"
        private const val PERSON_TV_CREDIT_ROUTE = "/person/%s/tv_credits"
        private const val POPULAR_TV_ROUTE = "/tv/popular"
        private const val SEARCH_TV_ROUTE = "/search/tv"
        private const val TV_DETAIL_ROUTE = "/tv/%s"
        private const val TV_CREDIT_ROUTE = "/tv/%s/credits"
        private const val LIMIT_LIST_ITEM = 15
    }
}