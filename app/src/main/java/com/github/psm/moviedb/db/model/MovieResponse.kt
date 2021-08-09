package com.github.psm.moviedb.db.model


import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
@Parcelize
data class MovieResponse(
    @SerialName("page")
    override val page: Int? = 0,
    @SerialName("results")
    override val results: List<Movie>? = listOf(),
    @SerialName("total_pages")
    override val totalPages: Int? = 0,
    @SerialName("total_results")
    override val totalResults: Int? = 0
) : Parcelable, BaseResponse<Movie>