package com.github.psm.moviedb.db.model.movie.credit


import android.os.Parcelable
import androidx.annotation.Keep
import com.github.psm.moviedb.db.converter.CastListConverter
import com.github.psm.moviedb.db.converter.CrewListConverter
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
data class MovieCredit(
    @Convert(converter = CastListConverter::class, dbType = String::class)
    @SerialName("cast")
    val cast: List<Cast>? = null,
    @Convert(converter = CrewListConverter::class, dbType = String::class)
    @SerialName("crew")
    val crew: List<Crew>? = null,
    @Id(assignable = true)
    @SerialName("id")
    var id: Long = 0
) : Parcelable