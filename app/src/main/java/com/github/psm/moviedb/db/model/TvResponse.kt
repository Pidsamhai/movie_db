package com.github.psm.moviedb.db.model


import android.os.Parcelable
import androidx.annotation.Keep
import com.github.psm.moviedb.db.model.tv.popular.Tv
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
@Parcelize
data class TvResponse(
    @SerialName("page")
    override val page: Int? = 0,
    @SerialName("results")
    override val results: List<Tv>? = listOf(),
    @SerialName("total_pages")
    override val totalPages: Int? = 0,
    @SerialName("total_results")
    override val totalResults: Int? = 0
) : Parcelable, BaseResponse<Tv>