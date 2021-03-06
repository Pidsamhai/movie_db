package com.github.psm.moviedb.repository

import com.github.psm.moviedb.db.Resource
import com.github.psm.moviedb.db.Response
import com.github.psm.moviedb.db.model.Movie
import com.github.psm.moviedb.db.model.MovieResponse
import com.github.psm.moviedb.db.model.TvResponse
import com.github.psm.moviedb.db.model.detail.MovieDetail
import com.github.psm.moviedb.db.model.genre.GenreResponse
import com.github.psm.moviedb.db.model.movie.credit.MovieCredit
import com.github.psm.moviedb.db.model.tv.credits.TvCredit
import com.github.psm.moviedb.db.model.tv.detail.TvDetail
import com.github.psm.moviedb.db.model.tv.popular.Tv
import com.github.psm.moviedb.db.model.tv.popular.TvPopularResponse
import com.github.psm.moviedb.db.model.upcoming.UpComingResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
interface TMDBRepository {
    fun getPopularMovieFlow(page: Int, refresh: Boolean): Flow<Resource<List<Movie>>>
    suspend fun getPopularMovie(page: Int): Response<MovieResponse>
    fun getMovieDetail(id: Long): Flow<Resource<MovieDetail>>
    suspend fun getGenreNormal()
    fun getGenres(): Flow<GenreResponse>
    fun getUpcomingMovieFlow(): Flow<UpComingResponse>
    suspend fun getUpcomingMovie(page: Int): Response<UpComingResponse>
    fun getMovieCredit(id: Long): Flow<Resource<MovieCredit>>
    suspend fun getPersonDetail(personId: Long)
    suspend fun getPersonTvCredit(personId: Long)
    suspend fun getPersonMovieCredit(personId: Long)
    suspend fun getPopularTv(page: Int): Response<TvPopularResponse>
    fun getPopularTvFlow(page: Int, refresh: Boolean): Flow<Resource<List<Tv>>>
    suspend fun searchTv(keyWord: String, page: Int = 1): Response<TvResponse>
    suspend fun searchMovie(keyWord: String, page: Int = 1): Response<MovieResponse>
    fun getTvDetail(id: Long): Flow<Resource<TvDetail>>
    fun getTvCredit(id: Long): Flow<Resource<TvCredit>>
}