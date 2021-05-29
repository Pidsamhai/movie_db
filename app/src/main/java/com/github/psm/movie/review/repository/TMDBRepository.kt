package com.github.psm.movie.review.repository

import com.github.psm.movie.review.db.model.Popular
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
interface TMDBRepository {
    fun getPopular(): Flow<Popular>
}