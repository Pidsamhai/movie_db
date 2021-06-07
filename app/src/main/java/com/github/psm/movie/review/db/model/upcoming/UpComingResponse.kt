package com.github.psm.movie.review.db.model.upcoming


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.github.psm.movie.review.db.model.Movie

@Keep
@Serializable
@Parcelize
data class UpComingResponse(
    @SerialName("dates")
    val dates: Dates? = Dates(),
    @SerialName("page")
    val page: Int? = 0,
    @SerialName("results")
    val movies: List<Movie>? = listOf(),
    @SerialName("total_pages")
    val totalPages: Int? = 0,
    @SerialName("total_results")
    val totalResults: Int? = 0
) : Parcelable