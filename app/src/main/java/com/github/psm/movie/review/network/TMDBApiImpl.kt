package com.github.psm.movie.review.network

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

class TMDBApiImpl: TMDBApi {

    private val client: HttpClient = HttpClient(OkHttp) {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
        defaultRequest {
            url.takeFrom(BASE_API_URL)
            parameter("api_key", BuildConfig.TMDB_API_KEY)
        }
    }

    override fun getPopular(): Flow<Popular> = flow {
        val result = client.get<Popular> {
            parameter("language", "en-US")
            parameter("page", 1)
        }
        emit(result)
    }

    companion object {
        private const val BASE_API_URL = "https://api.themoviedb.org/3"

        private const val POPULAR_ROUTE ="/movie/popular"
    }
}