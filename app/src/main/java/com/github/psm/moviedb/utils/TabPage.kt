package com.github.psm.moviedb.utils

interface TabPages {
    val pages: List<Int>
    val titles: List<String>
    val pageCount: Int
        get() = pages.size
    val defaultPage: Int
        get() = pages[0]
}