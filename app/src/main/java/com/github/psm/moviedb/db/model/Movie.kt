package com.github.psm.moviedb.db.model


import android.os.Parcelable
import androidx.annotation.Keep
import io.objectbox.annotation.*
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Entity
@Keep
@Serializable
@Parcelize
data class Movie(
    @Id
    @Transient
    var objId: Long = 0,
    @SerialName("adult")
    val adult: Boolean? = false,
    @SerialName("backdrop_path")
    val backdropPath: String? = "",
//    @SerialName("genre_ids")
//    val genreIds: List<Int>? = listOf(),
    @Uid(5582483785819779724L)
    @Unique(onConflict = ConflictStrategy.REPLACE)
    @SerialName("id")
    val id: Long = 0,
    @SerialName("original_language")
    val originalLanguage: String? = "",
    @SerialName("original_title")
    val originalTitle: String? = "",
    @SerialName("overview")
    val overview: String? = "",
    @SerialName("popularity")
    val popularity: Double? = 0.0,
    @SerialName("poster_path")
    val posterPath: String? = "",
    @SerialName("release_date")
    val releaseDate: String? = "",
    @SerialName("title")
    val title: String? = "",
    @SerialName("video")
    val video: Boolean? = false,
    @SerialName("vote_average")
    val voteAverage: Double? = 0.0,
    @SerialName("vote_count")
    val voteCount: Int? = 0
) : Parcelable, BaseVoteObject {

    override val voteStar: VoteStar
        get() = VoteStar(voteAverage)
}