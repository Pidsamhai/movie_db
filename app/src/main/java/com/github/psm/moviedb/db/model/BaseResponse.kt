package com.github.psm.moviedb.db.model


interface BaseResponse <T>{
    val page: Int?
    val results: List<T>?
    val totalPages: Int?
    val totalResults: Int?
}