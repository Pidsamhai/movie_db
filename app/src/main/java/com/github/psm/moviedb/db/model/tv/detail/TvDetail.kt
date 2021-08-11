package com.github.psm.moviedb.db.model.tv.detail


import android.os.Parcelable
import androidx.annotation.Keep
import com.github.psm.moviedb.db.converter.GenreListConverter
import com.github.psm.moviedb.db.converter.tv.*
import com.github.psm.moviedb.db.model.VoteStar
import com.github.psm.moviedb.db.model.genre.Genre
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity
@Keep
@Serializable
@Parcelize
data class TvDetail(
    @SerialName("backdrop_path")
    val backdropPath: String? = null,
    @Convert(converter = CreateByConverter::class, dbType = String::class)
    @SerialName("created_by")
    val createdBy: List<CreatedBy>? = listOf(),
    @Convert(converter = IntListConverter::class, dbType = String::class)
    @SerialName("episode_run_time")
    val episodeRunTime: List<Int>? = listOf(),
    @SerialName("first_air_date")
    val firstAirDate: String? = null,
    @Convert(converter = GenreListConverter::class, dbType = String::class)
    @SerialName("genres")
    val genres: List<Genre>? = listOf(),
    @SerialName("homepage")
    val homepage: String? = null,
    @Id(assignable = true)
    @SerialName("id")
    var id: Long = 0,
    @SerialName("in_production")
    val inProduction: Boolean? = null,
//    @SerialName("languages")
//    val languages: List<String>? = null,
    @SerialName("last_air_date")
    val lastAirDate: String? = null,
    @Convert(converter = EpisodeToAirConverter::class, dbType = String::class)
    @SerialName("last_episode_to_air")
    val lastEpisodeToAir: EpisodeToAir? = null,
    @SerialName("name")
    val name: String? = null,
    @Convert(converter = NetworkConverter::class, dbType = String::class)
    @SerialName("networks")
    val networks: List<Network>? = listOf(),
    @Convert(converter = EpisodeToAirConverter::class, dbType = String::class)
    @SerialName("next_episode_to_air")
    val nextEpisodeToAir: EpisodeToAir? = null,
    @SerialName("number_of_episodes")
    val numberOfEpisodes: Int? = null,
    @SerialName("number_of_seasons")
    val numberOfSeasons: Int? = null,
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
//    @SerialName("production_companies")
//    val productionCompanies: List<ProductionCompany>? = null,
//    @SerialName("production_countries")
//    val productionCountries: List<ProductionCountry>? = null,
    @Convert(converter = SeasonConverter::class, dbType = String::class)
    @SerialName("seasons")
    val seasons: List<Season>? = listOf(),
//    @SerialName("spoken_languages")
//    val spokenLanguages: List<SpokenLanguage>? = null,
    @SerialName("status")
    val status: String? = null,
    @SerialName("tagline")
    val tagline: String? = null,
    @SerialName("type")
    val type: String? = null,
    @SerialName("vote_average")
    val voteAverage: Double? = null,
    @SerialName("vote_count")
    val voteCount: Int? = null
) : Parcelable {
    @IgnoredOnParcel
    val voteStar: VoteStar
        get() = VoteStar(voteAverage)
}