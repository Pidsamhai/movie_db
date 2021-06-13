package com.github.psm.moviedb.db.model


import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
@Parcelize
data class BaseResponse(
    @SerialName("page")
    val page: Int? = 0,
    @SerialName("results")
    val movies: List<Movie>? = listOf(),
    @SerialName("total_pages")
    val totalPages: Int? = 0,
    @SerialName("total_results")
    val totalResults: Int? = 0
) : Parcelable