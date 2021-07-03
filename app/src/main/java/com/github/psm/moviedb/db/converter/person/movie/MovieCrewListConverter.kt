package com.github.psm.moviedb.db.converter.person.movie

import com.github.psm.moviedb.db.model.person.movie.MovieCrew
import com.github.psm.moviedb.utils.JsonX
import io.objectbox.converter.PropertyConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString

class MovieCrewListConverter : PropertyConverter<List<MovieCrew>, String> {
    override fun convertToEntityProperty(databaseValue: String?): List<MovieCrew> {
        return JsonX.decodeFromString(databaseValue ?: return emptyList())
    }

    override fun convertToDatabaseValue(entityProperty: List<MovieCrew>?): String {
        return JsonX.encodeToString(entityProperty)
    }
}