package com.github.psm.moviedb.db.model.tv.popular


import android.os.Parcelable
import androidx.annotation.Keep
import com.github.psm.moviedb.db.model.BaseVoteObject
import com.github.psm.moviedb.db.model.VoteStar
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity
@Keep
@Serializable
@Parcelize
data class Tv(
    @SerialName("backdrop_path")
    val backdropPath: String? = null,
    @SerialName("first_air_date")
    val firstAirDate: String? = null,
//    @SerialName("genre_ids")
//    val genreIds: List<Int>? = null,
    @Id(assignable = true)
    @SerialName("id")
    var id: Long = 0,
    @SerialName("name")
    val name: String? = null,
//    @SerialName("origin_country")
//    val originCountry: List<String>? = null,
    @SerialName("original_language")
    val originalLanguage: String? = null,
    @SerialName("original_name")
    val originalName: String? = null,
    @SerialName("overview")
    val overview: String? = null,
    @SerialName("popularity")
    val popularity: Double? = null,
    @SerialName("poster_path")
    val posterPath: String? = null,
    @SerialName("vote_average")
    val voteAverage: Double? = null,
    @SerialName("vote_count")
    val voteCount: Int? = null
) : Parcelable, BaseVoteObject {
    override val voteStar: VoteStar
        get() = VoteStar(voteAverage)
}