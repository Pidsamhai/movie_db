package com.github.psm.moviedb.db.model.detail


import android.os.Parcelable
import androidx.annotation.Keep
import com.github.psm.moviedb.db.model.VoteStar
import com.github.psm.moviedb.db.model.genre.Genre
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Entity
@Keep
@Serializable
@Parcelize
data class MovieDetail(
    @Id
    @Transient
    var objId: Long = 0,
    @SerialName("adult")
    val adult: Boolean? = false,
    @SerialName("backdrop_path")
    val backdropPath: String? = "",
//    @SerialName("belongs_to_collection")
//    val belongsToCollection: Any? = Any(),
    @SerialName("budget")
    val budget: Int? = 0,
    @SerialName("genres")
    val genres: List<Genre>? = listOf(),
    @SerialName("homepage")
    val homepage: String? = "",
    @SerialName("id")
    val id: Int? = 0,
    @SerialName("imdb_id")
    val imdbId: String? = "",
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
//    @SerialName("production_companies")
//    val productionCompanies: List<ProductionCompany>? = listOf(),
//    @SerialName("production_countries")
//    val productionCountries: List<ProductionCountry>? = listOf(),
    @SerialName("release_date")
    val releaseDate: String? = "",
    @SerialName("revenue")
    val revenue: Int? = 0,
    @SerialName("runtime")
    val runtime: Int? = 0,
//    @SerialName("spoken_languages")
//    val spokenLanguages: List<SpokenLanguage>? = listOf(),
    @SerialName("status")
    val status: String? = "",
    @SerialName("tagline")
    val tagline: String? = "",
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