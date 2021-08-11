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
import io.objectbox.kotlin.boxFor

class BoxStoreImpl : BoxStore {
    override val movie: Box<Movie>
        get() = ObjectBox.store.boxFor()
    override val movieDetail: Box<MovieDetail>
        get() = ObjectBox.store.boxFor()
    override val genre: Box<Genre>
        get() = ObjectBox.store.boxFor()
    override val bookmark: Box<Bookmark>
        get() = ObjectBox.store.boxFor()
    override val movieCredit: Box<MovieCredit>
        get() = ObjectBox.store.boxFor()
    override val person: Box<Person>
        get() = ObjectBox.store.boxFor()
    override val personTvCredit: Box<PersonTvCredit>
        get() = ObjectBox.store.boxFor()
    override val personMovieCredit: Box<PersonMovieCredit>
        get() = ObjectBox.store.boxFor()
    override val tv: Box<Tv>
        get() = ObjectBox.store.boxFor()
    override val tvDetail: Box<TvDetail>
        get() = ObjectBox.store.boxFor()
    override val tvCredit: Box<TvCredit>
        get() = ObjectBox.store.boxFor()
}