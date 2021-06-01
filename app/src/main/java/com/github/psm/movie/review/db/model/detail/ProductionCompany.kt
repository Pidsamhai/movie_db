package com.github.psm.movie.review.db.model.detail


import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
@Parcelize
data class ProductionCompany(
    @SerialName("id")
    val id: Int? = 0,
    @SerialName("logo_path")
    val logoPath: String? = null,
    @SerialName("name")
    val name: String? = "",
    @SerialName("origin_country")
    val originCountry: String? = ""
) : Parcelable