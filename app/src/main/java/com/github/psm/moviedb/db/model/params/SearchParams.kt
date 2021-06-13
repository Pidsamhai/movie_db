package com.github.psm.moviedb.db.model.params


import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class SearchParams(val keyword: String, val page: Int = 1): Parcelable