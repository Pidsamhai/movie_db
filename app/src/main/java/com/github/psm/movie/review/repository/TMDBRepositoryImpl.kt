package com.github.psm.movie.review.repository

import com.github.psm.movie.review.BuildConfig
import com.github.psm.movie.review.db.model.Popular
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

class TMDBRepositoryImpl : TMDBRepository {

    private val client = HttpClient(OkHttp) {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
        defaultRequest {
            host = BASE_API_HOST
            url {
                protocol = URLProtocol.HTTPS
            }
            parameter("api_key", BuildConfig.TMDB_API_KEY)
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

    companion object {
        private const val BASE_API_HOST = "api.themoviedb.org/3"
        private const val POPULAR_ROUTE ="/movie/popular"
    }
}