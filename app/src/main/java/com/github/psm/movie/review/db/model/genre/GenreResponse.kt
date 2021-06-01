package com.github.psm.movie.review.db.model.genre


import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
@Parcelize
data class GenreResponse(
    @SerialName("genres")
    val genres: List<Genre>? = listOf()
) : Parcelable