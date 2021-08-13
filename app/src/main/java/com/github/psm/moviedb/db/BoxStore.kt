package com.github.psm.moviedb.db

import com.github.psm.moviedb.db.model.Bookmark
import com.github.psm.moviedb.db.model.Movie
import com.github.psm.moviedb.db.model.detail.MovieDetail
import com.github.psm.moviedb.db.model.genre.Genre
import com.github.psm.moviedb.db.model.movie.credit.MovieCredit
import com.github.psm.moviedb.db.model.person.Person
import com.github.psm.moviedb.db.model.person.movie.PersonMovieCredit
import com.github.psm.moviedb.db.model.person.tv.PersonTvCredit
import com.github.psm.moviedb.db.model.tv.credits.TvCredit
import com.github.psm.moviedb.db.model.tv.detail.TvDetail
import com.github.psm.moviedb.db.model.tv.popular.Tv
import io.objectbox.Box
import io.objectbox.BoxStore

interface BoxStore {
    val movie: Box<Movie>
    val movieDetail: Box<MovieDetail>
    val genre: Box<Genre>
    val bookmark: Box<Bookmark>
    val movieCredit: Box<MovieCredit>
    val person: Box<Person>
    val personTvCredit: Box<PersonTvCredit>
    val personMovieCredit: Box<PersonMovieCredit>
    val tv: Box<Tv>
    val tvDetail: Box<TvDetail>
    val tvCredit: Box<TvCredit>
    val store: BoxStore
}