package com.github.psm.movie.review.db.model


import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
@Parcelize
data class Country(
    @SerialName("code")
    val code: String = "",
    @SerialName("name")
    val name: String = ""
) : Parcelable