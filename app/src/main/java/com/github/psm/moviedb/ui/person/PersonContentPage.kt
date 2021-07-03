package com.github.psm.moviedb.ui.person

object PersonContentPage {
    const val Biography = 0
    const val Movie = 1
    const val Tv = 2
    val pages = listOf(
        Biography,
        Movie,
        Tv
    )
    val PageCount = pages.size
    val title = listOf(
        "Biography",
        "Movie",
        "Tv"
    )
}