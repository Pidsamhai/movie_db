package com.github.psm.movie.review.db.model


import android.os.Parcelable
import androidx.annotation.Keep
import io.objectbox.annotation.ConflictStrategy
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Unique
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
    @Unique(onConflict = ConflictStrategy.REPLACE)
    @SerialName("id")
    val id: Int? = 0,
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
    get() {
        val realVote = (voteAverage ?: 0.0) / 2.0
        val starCount = floor(realVote).toInt()
        val hasHalf = (realVote - starCount) >= 0.5 && realVote != VoteStar.MAX_STAR.toDouble()
        val emptyStar = VoteStar.MAX_STAR - starCount
        return VoteStar(
            starCount = starCount,
            hasHalf = hasHalf,
            emptyStar = if (emptyStar == 1 && hasHalf) 0 else emptyStar
        )
    }
}

data class VoteStar(
    val starCount: Int,
    val hasHalf: Boolean,
    val emptyStar: Int
) {
    companion object {
        const val MAX_STAR = 5
    }
}