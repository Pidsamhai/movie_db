package com.github.psm.moviedb.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.github.psm.moviedb.db.BoxStore
import com.github.psm.moviedb.db.model.Bookmark
import com.github.psm.moviedb.db.model.Bookmark_
import com.github.psm.moviedb.db.model.detail.MovieDetail_
import com.github.psm.moviedb.utils.asLiveData
import timber.log.Timber
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val boxStore: BoxStore,
    private val pref: SharedPreferences
) : BookmarkRepository {

    override fun book(movieId: Long) {
        val bookmark = Bookmark(movieId = movieId)
        val movie = boxStore
            .movieDetail
            .query()
            .equal(MovieDetail_.id, movieId)
            .build()
            .findFirst()
        bookmark.movieDetail.target = movie ?: return
        boxStore.bookmark.put(bookmark)
    }

    override fun unBook(movieId: Long) {
        boxStore.bookmark.remove(movieId)
        pref.edit {
            putLong(LATEST_REMOVE_ID, movieId)
        }
    }

    override fun undoUnBookmark() {
        val movieId = pref.getLong(LATEST_REMOVE_ID, 0L)
        if (movieId == 0L) return
        this.book(movieId)
        pref.edit {
            remove(LATEST_REMOVE_ID)
        }
        Timber.i("Undo: $movieId")
    }

    override fun getBookmarks(): LiveData<List<Bookmark>> {
        return boxStore.bookmark.query().asLiveData()
    }

    override fun bookState(movieId: Long): LiveData<Boolean> {
        return boxStore
            .bookmark
            .query()
            .equal(Bookmark_.movieId, movieId)
            .asLiveData()
            .map {
                it.firstOrNull() != null
            }
    }

    override fun getBookmark(movieId: Long): Bookmark? {
        return boxStore.bookmark[movieId]
    }

    companion object {
        private const val LATEST_REMOVE_ID = "latest_remove_id"
    }
}