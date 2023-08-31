package com.github.psm.moviedb.db.converter.person.movie

import com.github.psm.moviedb.db.model.person.movie.MovieCast
import com.github.psm.moviedb.utils.JsonX
import io.objectbox.converter.PropertyConverter
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString

class MovieCastListConverter : PropertyConverter<List<MovieCast>, String> {
    @OptIn(ExperimentalSerializationApi::class)
    override fun convertToEntityProperty(databaseValue: String?): List<MovieCast> {
        return JsonX.decodeFromString(databaseValue ?: return emptyList())
    }

    @OptIn(ExperimentalSerializationApi::class)
    override fun convertToDatabaseValue(entityProperty: List<MovieCast>?): String {
        return JsonX.encodeToString(entityProperty)
    }
}