package com.github.psm.movie.review.network

import com.github.psm.movie.review.db.model.Popular
import kotlinx.coroutines.flow.Flow

interface TMDBApi {
    fun getPopular(): Flow<Popular>
}