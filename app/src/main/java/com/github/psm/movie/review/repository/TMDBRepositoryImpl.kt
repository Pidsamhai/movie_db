package com.github.psm.movie.review.repository

import com.github.psm.movie.review.db.model.Popular
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
import okhttp3.logging.HttpLoggingInterceptor
import kotlinx.serialization.json.Json as KJson

class TMDBRepositoryImpl : TMDBRepository {

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
            addInterceptor(
                HttpLoggingInterceptor().apply {
                    // level = HttpLoggingInterceptor.Level.HEADERS
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
        }
    }

    override fun getPopular(): Flow<Popular> = flow {
        val result = client.get<Popular>(path = POPULAR_ROUTE) {
            parameter("language", "en-US")
            parameter("page", 1)
        }
        emit(result)
    }

    override fun getMovieDetail(movieId: String): Flow<MovieDetail> = flow {
        val result = client.get<MovieDetail>(path = "${MOVIE_DETAIL_ROUTE}/$movieId")
        emit(result)
    }

    override fun getGenres(): Flow<GenreResponse> = flow {
        val result = client.get<GenreResponse>(path = GENRES_ROUTE)
        emit(result)
    }

    companion object {
        private const val BASE_API_HOST = "api.themoviedb.org/3"
        private const val POPULAR_ROUTE ="/movie/popular"
        private const val MOVIE_DETAIL_ROUTE = "/movie"
        private const val GENRES_ROUTE = "/genre/movie/list"
    }
}