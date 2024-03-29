package com.github.psm.moviedb.db.converter

import com.github.psm.moviedb.db.model.shared.credit.Crew
import com.github.psm.moviedb.utils.JsonX
import io.objectbox.converter.PropertyConverter
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString

class CrewListConverter : PropertyConverter<List<Crew>, String> {
    @OptIn(ExperimentalSerializationApi::class)
    override fun convertToEntityProperty(databaseValue: String?): List<Crew> {
        return JsonX.decodeFromString(databaseValue ?: return emptyList())
    }

    @OptIn(ExperimentalSerializationApi::class)
    override fun convertToDatabaseValue(entityProperty: List<Crew>?): String {
        return JsonX.encodeToString(entityProperty)
    }
}