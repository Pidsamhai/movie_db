package com.github.psm.moviedb.db.model.shared.detail


import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
@Parcelize
data class ProductionCountry(
    @SerialName("iso_3166_1")
    val iso31661: String? = "",
    @SerialName("name")
    val name: String? = ""
) : Parcelable