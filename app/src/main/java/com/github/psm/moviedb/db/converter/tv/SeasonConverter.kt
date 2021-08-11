package com.github.psm.moviedb.db.converter.tv

import com.github.psm.moviedb.db.model.tv.detail.Season
import com.github.psm.moviedb.utils.JsonX
import io.objectbox.converter.PropertyConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString

class SeasonConverter : PropertyConverter<List<Season>, String> {
    override fun convertToEntityProperty(databaseValue: String?): List<Season> {
        return JsonX.decodeFromString(databaseValue ?: return emptyList())
    }

    override fun convertToDatabaseValue(entityProperty: List<Season>?): String {
        return JsonX.encodeToString(entityProperty)
    }
}