package com.github.psm.movie.review.repository

import com.github.psm.movie.review.db.Response
import com.github.psm.movie.review.db.model.BaseResponse
import com.github.psm.movie.review.db.model.genre.GenreResponse
import com.github.psm.movie.review.db.model.upcoming.UpComingResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
interface TMDBRepository {
    suspend fun getPopular(page: Int): Response<BaseResponse>
    suspend fun getMovieDetail(movieId: Int)
    suspend fun getGenreNormal()
    fun getGenres(): Flow<GenreResponse>
    fun search(keyWord: String, page: Int = 1): Flow<BaseResponse>
    fun getUpComingFlow(): Flow<UpComingResponse>
    suspend fun getUpComing(page: Int): Response<UpComingResponse>
}