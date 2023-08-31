package com.github.psm.moviedb.db.converter.tv

import com.github.psm.moviedb.db.model.tv.detail.EpisodeToAir
import com.github.psm.moviedb.utils.JsonX
import io.objectbox.converter.PropertyConverter
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString

class EpisodeToAirConverter: PropertyConverter<EpisodeToAir?, String> {
    @OptIn(ExperimentalSerializationApi::class)
    override fun convertToEntityProperty(databaseValue: String?): EpisodeToAir {
        return JsonX.decodeFromString(databaseValue ?: "{}")
    }

    @OptIn(ExperimentalSerializationApi::class)
    override fun convertToDatabaseValue(entityProperty: EpisodeToAir?): String {
        return JsonX.encodeToString(entityProperty)
    }
}