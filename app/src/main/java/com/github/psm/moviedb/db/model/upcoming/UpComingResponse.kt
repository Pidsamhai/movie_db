package com.github.psm.moviedb.db.model.upcoming


import android.os.Parcelable
import androidx.annotation.Keep
import com.github.psm.moviedb.db.model.BaseResponse
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
    override val page: Int? = 0,
    @SerialName("results")
    override val results: List<Movie>? = listOf(),
    @SerialName("total_pages")
    override val totalPages: Int? = 0,
    @SerialName("total_results")
    override val totalResults: Int? = 0
) : Parcelable, BaseResponse<Movie>