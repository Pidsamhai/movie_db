package com.github.psm.movie.review.repository

import com.github.psm.movie.review.db.model.Popular
import com.github.psm.movie.review.db.model.detail.MovieDetail
import com.github.psm.movie.review.db.model.genre.GenreResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
interface TMDBRepository {
    fun getPopular(): Flow<Popular>
    fun getMovieDetail(movieId: String): Flow<MovieDetail>
    fun getGenres(): Flow<GenreResponse>
}