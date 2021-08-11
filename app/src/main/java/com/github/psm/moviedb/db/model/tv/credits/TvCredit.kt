package com.github.psm.moviedb.db.model.tv.credits


import android.os.Parcelable
import androidx.annotation.Keep
import com.github.psm.moviedb.db.converter.CastListConverter
import com.github.psm.moviedb.db.converter.CrewListConverter
import com.github.psm.moviedb.db.model.shared.credit.Cast
import com.github.psm.moviedb.db.model.shared.credit.Credit
import com.github.psm.moviedb.db.model.shared.credit.Crew
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
data class TvCredit(
    @Convert(converter = CastListConverter::class, dbType = String::class)
    @SerialName("cast")
    override val cast: List<Cast>? = null,
    @Convert(converter = CrewListConverter::class, dbType = String::class)
    @SerialName("crew")
    override val crew: List<Crew>? = null,
    @Id(assignable = true)
    @SerialName("id")
    override var id: Long = 0
) : Parcelable, Credit