package com.github.psm.moviedb.db.converter

import com.github.psm.moviedb.db.model.movie.credit.Crew
import com.github.psm.moviedb.utils.JsonX
import io.objectbox.converter.PropertyConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString

class CrewListConverter : PropertyConverter<List<Crew>, String> {
    override fun convertToEntityProperty(databaseValue: String?): List<Crew> {
        return JsonX.decodeFromString(databaseValue ?: return emptyList())
    }

    override fun convertToDatabaseValue(entityProperty: List<Crew>?): String {
        return JsonX.encodeToString(entityProperty)
    }
}