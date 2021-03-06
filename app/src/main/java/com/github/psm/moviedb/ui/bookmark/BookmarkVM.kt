package com.github.psm.moviedb.ui.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.github.psm.moviedb.db.model.BookMarkType
import com.github.psm.moviedb.repository.BookmarkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookmarkVM @Inject constructor(
    private val repository: BookmarkRepository
): ViewModel() {
    val bookmarks: LiveData<List<BookMarkType>> = repository
        .getBookmarks()

    fun booking(id: Long, isMovie: Boolean) = repository.book(id, isMovie)
    fun unBook(id: Long, isMovie: Boolean) = repository.unBook(id, isMovie)
    fun undoUnBookmark() = repository.undoUnBookmark()
}