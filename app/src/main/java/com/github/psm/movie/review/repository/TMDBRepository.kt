package com.github.psm.movie.review.repository

import com.github.psm.movie.review.db.model.BaseResponse
import com.github.psm.movie.review.db.model.genre.GenreResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
interface TMDBRepository {
    suspend fun getPopular()
    suspend fun getMovieDetail(movieId: Int)
    suspend fun getGenreNormal()
    fun getGenres(): Flow<GenreResponse>
    fun search(keyWord: String, page: Int = 1): Flow<BaseResponse>
}