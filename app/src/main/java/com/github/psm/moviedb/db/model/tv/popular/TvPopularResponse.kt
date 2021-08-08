package com.github.psm.moviedb.db.model.tv.popular


import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
@Parcelize
data class TvPopularResponse(
    @SerialName("page")
    val page: Int? = null,
    @SerialName("results")
    val tvs: List<Tv>? = null,
    @SerialName("total_pages")
    val totalPages: Int? = null,
    @SerialName("total_results")
    val totalResults: Int? = null
) : Parcelable