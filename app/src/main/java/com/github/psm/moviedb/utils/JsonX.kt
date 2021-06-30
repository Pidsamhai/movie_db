package com.github.psm.moviedb.utils

val JsonX = kotlinx.serialization.json.Json {
    isLenient = false
    ignoreUnknownKeys = true
    allowSpecialFloatingPointValues = true
    useArrayPolymorphism = false
}