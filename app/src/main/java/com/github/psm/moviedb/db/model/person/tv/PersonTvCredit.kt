package com.github.psm.moviedb.db.model.person.tv


import android.os.Parcelable
import androidx.annotation.Keep
import com.github.psm.moviedb.db.converter.person.tv.TvCastListConverter
import com.github.psm.moviedb.db.converter.person.tv.TvCrewListConverter
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
data class PersonTvCredit(
    @Convert(converter = TvCastListConverter::class, dbType = String::class)
    @SerialName("cast")
    val tvCast: List<TvCast>? = null,
    @Convert(converter = TvCrewListConverter::class, dbType = String::class)
    @SerialName("crew")
    val tvCrew: List<TvCrew>? = null,
    @Id(assignable = true)
    @SerialName("id")
    var id: Long = 0
) : Parcelable