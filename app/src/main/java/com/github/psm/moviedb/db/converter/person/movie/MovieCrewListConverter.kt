package com.github.psm.moviedb.db.converter.person.movie

import com.github.psm.moviedb.db.model.person.movie.MovieCrew
import com.github.psm.moviedb.utils.JsonX
import io.objectbox.converter.PropertyConverter
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString

class MovieCrewListConverter : PropertyConverter<List<MovieCrew>, String> {
    @OptIn(ExperimentalSerializationApi::class)
    override fun convertToEntityProperty(databaseValue: String?): List<MovieCrew> {
        return JsonX.decodeFromString(databaseValue ?: return emptyList())
    }

    @OptIn(ExperimentalSerializationApi::class)
    override fun convertToDatabaseValue(entityProperty: List<MovieCrew>?): String {
        return JsonX.encodeToString(entityProperty)
    }
}