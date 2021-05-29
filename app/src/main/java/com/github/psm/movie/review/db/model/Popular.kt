package com.github.psm.movie.review.db.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Keep
@Serializable
@Parcelize
data class Popular(
    @SerialName("page")
    val page: Int? = 0,
    @SerialName("results")
    val results: List<Result>? = listOf(),
    @SerialName("total_pages")
    val totalPages: Int? = 0,
    @SerialName("total_results")
    val totalResults: Int? = 0
) : Parcelable