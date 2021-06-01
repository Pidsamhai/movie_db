package com.github.psm.movie.review.db.model.detail


import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
@Parcelize
data class SpokenLanguage(
    @SerialName("english_name")
    val englishName: String? = "",
    @SerialName("iso_639_1")
    val iso6391: String? = "",
    @SerialName("name")
    val name: String? = ""
) : Parcelable