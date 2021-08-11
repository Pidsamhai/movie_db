package com.github.psm.moviedb.repository

import com.github.psm.moviedb.db.Resource
import com.github.psm.moviedb.db.Response
import com.github.psm.moviedb.db.model.MovieResponse
import com.github.psm.moviedb.db.model.TvResponse
import com.github.psm.moviedb.db.model.genre.GenreResponse
import com.github.psm.moviedb.db.model.tv.credits.TvCredit
import com.github.psm.moviedb.db.model.tv.detail.TvDetail
import com.github.psm.moviedb.db.model.tv.popular.TvPopularResponse
import com.github.psm.moviedb.db.model.upcoming.UpComingResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
interface TMDBRepository {
    suspend fun getPopularMovie(page: Int): Response<MovieResponse>
    suspend fun getMovieDetail(movieId: Long)
    suspend fun getGenreNormal()
    fun getGenres(): Flow<GenreResponse>
    fun getUpcomingMovieFlow(): Flow<UpComingResponse>
    suspend fun getUpcomingMovie(page: Int): Response<UpComingResponse>
    suspend fun getMovieCredit(movieId: Long)
    suspend fun getPersonDetail(personId: Long)
    suspend fun getPersonTvCredit(personId: Long)
    suspend fun getPersonMovieCredit(personId: Long)
    suspend fun getPopularTv(page: Int): Response<TvPopularResponse>
    suspend fun searchTv(keyWord: String, page: Int = 1): Response<TvResponse>
    suspend fun searchMovie(keyWord: String, page: Int = 1): Response<MovieResponse>
    fun getTvDetail(id: Long): Flow<Resource<TvDetail>>
    fun getTvCredit(id: Long): Flow<Resource<TvCredit>>
}