package com.github.psm.moviedb.db.model.person.movie


import android.os.Parcelable
import androidx.annotation.Keep
import com.github.psm.moviedb.db.converter.person.movie.MovieCastListConverter
import com.github.psm.moviedb.db.converter.person.movie.MovieCrewListConverter
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity
@Keep
@Serializable
@Parcelize
data class PersonMovieCredit(
    @Convert(converter = MovieCastListConverter::class, dbType = String::class)
    @SerialName("cast")
    val movieCast: List<MovieCast>? = null,
    @Convert(converter = MovieCrewListConverter::class, dbType = String::class)
    @SerialName("crew")
    val movieCrew: List<MovieCrew>? = null,
    @Id(assignable = true)
    @SerialName("id")
    var id: Long = 0
) : Parcelable