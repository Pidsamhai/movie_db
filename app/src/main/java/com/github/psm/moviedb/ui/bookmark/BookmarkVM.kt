package com.github.psm.moviedb.ui.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.github.psm.moviedb.db.model.detail.MovieDetail
import com.github.psm.moviedb.repository.BookmarkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookmarkVM @Inject constructor(
    private val repository: BookmarkRepository
): ViewModel() {
    val bookmarks: LiveData<List<MovieDetail>> = repository
        .getBookmarks()
        .map { bookmarks ->
            bookmarks.map { bookmark ->
                bookmark.movieDetail.target
            }
        }
    fun booking(movieId: Long) = repository.book(movieId)
    fun bookState(movieId: Long): LiveData<Boolean> = repository.bookState(movieId)
    fun unBook(movieId: Long) = repository.unBook(movieId)
    fun undoUnBookmark() = repository.undoUnBookmark()
}