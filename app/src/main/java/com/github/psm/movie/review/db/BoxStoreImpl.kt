package com.github.psm.movie.review.db

import com.github.psm.movie.review.db.model.Movie
import com.github.psm.movie.review.db.model.detail.MovieDetail
import com.github.psm.movie.review.db.model.genre.Genre
import io.objectbox.Box
import io.objectbox.kotlin.boxFor

class BoxStoreImpl : BoxStore {
    override val movie: Box<Movie>
        get() = ObjectBox.store.boxFor()
    override val movieDetail: Box<MovieDetail>
        get() = ObjectBox.store.boxFor()
    override val genre: Box<Genre>
        get() = ObjectBox.store.boxFor()
}