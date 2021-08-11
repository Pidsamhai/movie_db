package com.github.psm.moviedb.db.converter.tv

import com.github.psm.moviedb.utils.JsonX
import io.objectbox.converter.PropertyConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString

class IntListConverter: PropertyConverter<List<Int>, String> {
    override fun convertToEntityProperty(databaseValue: String?): List<Int>? {
        return JsonX.decodeFromString(databaseValue ?: return emptyList())
    }

    override fun convertToDatabaseValue(entityProperty: List<Int>?): String {
        return JsonX.encodeToString(entityProperty)
    }
}