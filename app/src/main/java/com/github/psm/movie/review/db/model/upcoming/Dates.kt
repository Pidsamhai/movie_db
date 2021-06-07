package com.github.psm.movie.review.db.model.upcoming


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Keep
@Serializable
@Parcelize
data class Dates(
    @SerialName("maximum")
    val maximum: String? = "",
    @SerialName("minimum")
    val minimum: String? = ""
) : Parcelable