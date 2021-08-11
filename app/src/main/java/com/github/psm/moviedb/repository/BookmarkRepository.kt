package com.github.psm.moviedb.repository

import androidx.lifecycle.LiveData
import com.github.psm.moviedb.db.model.BookMarkType
import com.github.psm.moviedb.db.model.Bookmark

interface BookmarkRepository {
    fun book(id: Long, isMovie: Boolean)
    fun unBook(id: Long)
    fun getBookmarks(): LiveData<List<BookMarkType>>
    fun getBookmark(id: Long): Bookmark?
    fun bookState(id: Long): LiveData<Boolean>
    fun undoUnBookmark()
}