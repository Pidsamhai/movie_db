package com.github.psm.moviedb.db.model.upcoming


import android.os.Parcelable
import androidx.annotation.Keep
import com.github.psm.moviedb.db.model.Movie
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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