package com.github.psm.movie.review.utils

private const val BASE_IMG_URL = "https://image.tmdb.org/t/p/original"

fun String.toImgUrl() = BASE_IMG_URL + this