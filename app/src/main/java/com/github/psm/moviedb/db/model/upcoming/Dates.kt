package com.github.psm.moviedb.db.model.upcoming


import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
@Parcelize
data class Dates(
    @SerialName("maximum")
    val maximum: String? = "",
    @SerialName("minimum")
    val minimum: String? = ""
) : Parcelable