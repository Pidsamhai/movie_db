package com.github.psm.moviedb.db.model.genre


import android.os.Parcelable
import androidx.annotation.Keep
import io.objectbox.annotation.ConflictStrategy
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Unique
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Entity
@Keep
@Serializable
@Parcelize
data class Genre(
    @Id
    @Transient
    var objId: Long = 0,
    @Unique(onConflict = ConflictStrategy.REPLACE)
    @SerialName("id")
    val id: Int? = 0,
    @SerialName("name")
    val name: String? = ""
) : Parcelable