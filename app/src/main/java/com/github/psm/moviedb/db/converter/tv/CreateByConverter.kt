package com.github.psm.moviedb.db.converter.tv

import com.github.psm.moviedb.db.model.tv.detail.CreatedBy
import com.github.psm.moviedb.utils.JsonX
import io.objectbox.converter.PropertyConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString

class CreateByConverter: PropertyConverter<List<CreatedBy>, String> {
    override fun convertToEntityProperty(databaseValue: String?): List<CreatedBy>? {
        return JsonX.decodeFromString(databaseValue ?: return emptyList())
    }

    override fun convertToDatabaseValue(entityProperty: List<CreatedBy>?): String {
        return JsonX.encodeToString(entityProperty)
    }
}