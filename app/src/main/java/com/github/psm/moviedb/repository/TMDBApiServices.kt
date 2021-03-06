package com.github.psm.moviedb.repository

import com.github.psm.moviedb.BuildConfig
import com.github.psm.moviedb.utils.JsonX
import com.github.psm.moviedb.utils.Keys
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor

object TMDBApiServices {

    private const val BASE_API_HOST = "api.themoviedb.org/3"

    fun getInstance(settingRepository: SettingRepository) = HttpClient(OkHttp) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(JsonX)
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
            addNetworkInterceptor(Interceptor.invoke {
                val req = it.request()
                val url = req.url
                    .newBuilder()
                    .addQueryParameter("language", settingRepository.languageCode)
                    .addQueryParameter("region", settingRepository.regionCode)
                    .build()
                val newReq = req.newBuilder().url(url).build()
                it.proceed(newReq)
            })
            if (BuildConfig.DEBUG) {
                addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
            }
        }
    }
}