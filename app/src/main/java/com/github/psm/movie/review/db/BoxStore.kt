package com.github.psm.movie.review.db

import com.github.psm.movie.review.db.model.Movie
import com.github.psm.movie.review.db.model.detail.MovieDetail
import com.github.psm.movie.review.db.model.genre.Genre
import io.objectbox.Box

interface BoxStore {
    val movie: Box<Movie>
    val movieDetail: Box<MovieDetail>
    val genre: Box<Genre>
}