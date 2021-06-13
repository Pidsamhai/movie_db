package com.github.psm.moviedb.repository

import com.github.psm.moviedb.db.Response
import com.github.psm.moviedb.db.model.BaseResponse
import com.github.psm.moviedb.db.model.genre.GenreResponse
import com.github.psm.moviedb.db.model.upcoming.UpComingResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
interface TMDBRepository {
    suspend fun getPopular(page: Int): Response<BaseResponse>
    suspend fun getMovieDetail(movieId: Long)
    suspend fun getGenreNormal()
    fun getGenres(): Flow<GenreResponse>
    fun search(keyWord: String, page: Int = 1): Flow<BaseResponse>
    fun getUpComingFlow(): Flow<UpComingResponse>
    suspend fun getUpComing(page: Int): Response<UpComingResponse>
}