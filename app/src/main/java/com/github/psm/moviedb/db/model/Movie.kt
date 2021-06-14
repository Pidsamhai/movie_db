package com.github.psm.moviedb.db.model


import android.os.Parcelable
import androidx.annotation.Keep
import io.objectbox.annotation.*
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlin.math.floor

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
) : Parcelable {

    @IgnoredOnParcel
    val voteStar: VoteStar
        get() = VoteStar(voteAverage)
}

class VoteStar(voteAverage: Double?) {
    val starCount: Int
    val hasHalf: Boolean
    val emptyStar: Int

    init {
        val realVote = (voteAverage ?: 0.0) / 2.0
        val _starCount = floor(realVote).toInt()
        val _hasHalf = (realVote - _starCount) >= 0.5 && realVote != MAX_STAR.toDouble()
        val _emptyStar = if (_hasHalf && _starCount == 4) 1 else MAX_STAR - _starCount
        starCount = _starCount
        hasHalf = _hasHalf
        emptyStar = if (hasHalf) _emptyStar - 1 else _emptyStar
    }

    companion object {
        const val MAX_STAR = 5
    }
}