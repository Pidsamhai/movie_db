package com.github.psm.moviedb.db.converter

import com.github.psm.moviedb.db.model.genre.Genre
import com.github.psm.moviedb.utils.JsonX
import io.objectbox.converter.PropertyConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString

class GenreListConverter : PropertyConverter<List<Genre>, String>  {
    override fun convertToEntityProperty(databaseValue: String?): List<Genre> {
        return JsonX.decodeFromString(databaseValue ?: return emptyList())
    }

    override fun convertToDatabaseValue(entityProperty: List<Genre>?): String {
        return JsonX.encodeToString(entityProperty)
    }
}