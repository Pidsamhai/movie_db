package com.github.psm.moviedb.db.model

import com.github.psm.moviedb.db.model.detail.MovieDetail
import com.github.psm.moviedb.db.model.tv.detail.TvDetail
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Uid
import io.objectbox.relation.ToOne
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Entity
@Serializable
data class Bookmark(
    @Uid(7593354813093938104L)
    @Id(assignable = true)
    @SerialName("uid")
    var uid: Long = 0,
    @Uid(3714826150638432069L)
    @SerialName("id")
    val id: Long? = null, // Set Default for old Data
    @SerialName("isMovie")
    val isMovie: Boolean = true
) {
    @Transient
    lateinit var movieDetail: ToOne<MovieDetail?>

    @Transient
    lateinit var tvDetail: ToOne<TvDetail?>
}

sealed class BookMarkType {
    data class Movie(val data: MovieDetail) : BookMarkType()
    data class Tv(val data: TvDetail) : BookMarkType()
}