package com.github.psm.moviedb.db

import com.github.psm.moviedb.db.model.Bookmark
import com.github.psm.moviedb.db.model.Movie
import com.github.psm.moviedb.db.model.detail.MovieDetail
import com.github.psm.moviedb.db.model.genre.Genre
import com.github.psm.moviedb.db.model.movie.credit.MovieCredit
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
}