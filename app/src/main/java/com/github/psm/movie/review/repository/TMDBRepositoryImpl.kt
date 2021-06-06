package com.github.psm.movie.review.repository

import android.content.SharedPreferences
import com.github.psm.movie.review.db.BoxStore
import com.github.psm.movie.review.db.model.BaseResponse
import com.github.psm.movie.review.db.model.detail.MovieDetail
import com.github.psm.movie.review.db.model.genre.GenreResponse
import com.github.psm.movie.review.utils.Keys
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.Interceptor
import timber.log.Timber
import javax.inject.Inject
import kotlinx.serialization.json.Json as KJson

class TMDBRepositoryImpl @Inject constructor(
    private val boxStore: BoxStore,
    private val pref: SharedPreferences
) : TMDBRepository {

    private val client = HttpClient(OkHttp) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(
                KJson {
                    isLenient = false
                    ignoreUnknownKeys = true
                    allowSpecialFloatingPointValues = true
                    useArrayPolymorphism = false
                }
            )
        }
        defaultRequest {
            host = BASE_API_HOST
            url {
                protocol = URLProtocol.HTTPS
            }
            parameter("api_key", Keys.TMDBApiKey())
            contentType(ContentType.Application.Json)
        }
        engine {
//            addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
            addNetworkInterceptor(Interceptor.invoke {
                val req = it.request()
                val url = req.url
                    .newBuilder()
                    .addQueryParameter(
                        "language", pref.getString("language","en-US") ?: "en-US"
                    )
                    .build()
                val newReq = req.newBuilder().url(url).build()
                Timber.i("==== Start ====")
                Timber.i("-> Request: ${newReq.method} -> ${newReq.url}")
                Timber.i("==== End ====")
                val res = it.proceed(newReq)
                Timber.i("==== Start ====")
                Timber.i("<- Response: ${res.code}")
                Timber.i("==== End ====")
                res
            })
        }
    }

    override suspend fun getPopular() {
        try {
            val result = client.get<BaseResponse>(path = POPULAR_ROUTE) {
                parameter("page", 1)
            }
            result.movies?.let { boxStore.movie.put(it) }
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    override suspend fun getMovieDetail(movieId: Int) {
        try {
            val result = client.get<MovieDetail>(path = "${MOVIE_DETAIL_ROUTE}/$movieId")
            boxStore.movieDetail.put(result)
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    override fun getGenres(): Flow<GenreResponse> = flow {
        val result = client.get<GenreResponse>(path = GENRES_ROUTE)
        emit(result)
    }

    override suspend fun getGenreNormal() {
        try {
            val result = client.get<GenreResponse>(path = GENRES_ROUTE)
            result.genres?.let { boxStore.genre.put(it) }
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    override fun search(keyWord: String, page: Int): Flow<BaseResponse> = flow {
        try {
            val result = client.get<BaseResponse>(path = SEARCH_ROUTE) {
                parameter("query", keyWord)
                parameter("page", page)
                parameter("include_adult", true)
            }
            emit(result)
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    companion object {
        private const val BASE_API_HOST = "api.themoviedb.org/3"
        private const val POPULAR_ROUTE = "/movie/popular"
        private const val MOVIE_DETAIL_ROUTE = "/movie"
        private const val GENRES_ROUTE = "/genre/movie/list"
        private const val SEARCH_ROUTE = "/search/movie"
    }
}