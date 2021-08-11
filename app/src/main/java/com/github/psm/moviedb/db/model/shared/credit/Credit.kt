package com.github.psm.moviedb.db.model.shared.credit

interface Credit {
    val cast: List<Cast>?
    val crew: List<Crew>?
    var id: Long
}