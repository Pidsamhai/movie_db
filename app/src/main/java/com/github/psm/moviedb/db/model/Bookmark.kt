package com.github.psm.moviedb.db.model

import com.github.psm.moviedb.db.model.detail.MovieDetail
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.TargetIdProperty
import io.objectbox.relation.ToOne

@Entity
data class Bookmark(
    @Id(assignable = true)
    var movieId: Long = 0,
) {
    @TargetIdProperty("id")
    lateinit var movieDetail: ToOne<MovieDetail>
}