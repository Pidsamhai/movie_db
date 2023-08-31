package com.github.psm.moviedb.db.converter

import com.github.psm.moviedb.db.model.shared.credit.Cast
import com.github.psm.moviedb.utils.JsonX
import io.objectbox.converter.PropertyConverter
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString

@OptIn(ExperimentalSerializationApi::class)
class CastListConverter : PropertyConverter<List<Cast>, String> {
    override fun convertToEntityProperty(databaseValue: String?): List<Cast> {
        return JsonX.decodeFromString(databaseValue ?: return emptyList())
    }

    override fun convertToDatabaseValue(entityProperty: List<Cast>?): String {
        return JsonX.encodeToString(entityProperty)
    }
}