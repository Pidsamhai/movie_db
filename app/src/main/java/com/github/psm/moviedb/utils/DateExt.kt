package com.github.psm.moviedb.utils

import kotlinx.datetime.*

fun String.toAge(unit: DateTimeUnit.DateBased = DateTimeUnit.YEAR): String  {
    return this.toLocalDate().until(
        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
        unit
    ).toString()
}