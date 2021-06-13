package com.github.psm.moviedb.repository

import androidx.lifecycle.LiveData
import com.github.psm.moviedb.db.model.Bookmark

interface BookmarkRepository {
    fun book(movieId: Long)
    fun unBook(movieId: Long)
    fun getBookmarks(): LiveData<List<Bookmark>>
    fun getBookmark(movieId: Long): Bookmark?
    fun bookState(movieId: Long): LiveData<Boolean>
}