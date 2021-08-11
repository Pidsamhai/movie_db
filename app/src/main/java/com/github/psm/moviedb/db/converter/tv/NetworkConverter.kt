package com.github.psm.moviedb.db.converter.tv

import com.github.psm.moviedb.db.model.tv.detail.Network
import com.github.psm.moviedb.utils.JsonX
import io.objectbox.converter.PropertyConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString

class NetworkConverter : PropertyConverter<List<Network>, String> {
    override fun convertToEntityProperty(databaseValue: String?): List<Network>? {
        return JsonX.decodeFromString(databaseValue ?: return emptyList())
    }

    override fun convertToDatabaseValue(entityProperty: List<Network>?): String {
        return JsonX.encodeToString(entityProperty)
    }
}