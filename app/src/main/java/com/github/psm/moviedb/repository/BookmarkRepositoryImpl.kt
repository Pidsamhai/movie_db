package com.github.psm.moviedb.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.github.psm.moviedb.db.BoxStore
import com.github.psm.moviedb.db.model.BookMarkType
import com.github.psm.moviedb.db.model.Bookmark
import com.github.psm.moviedb.db.model.Bookmark_
import com.github.psm.moviedb.utils.JsonX
import com.github.psm.moviedb.utils.asLiveData
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import timber.log.Timber
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val boxStore: BoxStore,
    private val pref: SharedPreferences
) : BookmarkRepository {

    override fun book(id: Long, isMovie: Boolean) {
        val bookmark = Bookmark(movieId = id, isMovie = isMovie)
        bookmark.movieDetail.target = boxStore.movieDetail[id]
        bookmark.tvDetail.target = boxStore.tvDetail[id]
        boxStore.bookmark.put(bookmark)
        val item = boxStore.bookmark[id]
        Timber.d("Booked %s %s", item.movieDetail.target, item.tvDetail.target)
    }

    override fun unBook(id: Long) {
        val item = boxStore.bookmark[id]
        saveLatestRemoveItem(item)
        boxStore.bookmark.remove(id)
    }

    override fun undoUnBookmark() {
        val item = getLatestRemoveItem() ?: return
        book(item.movieId, item.isMovie)
        removeLatestRemoveItem()
        Timber.i("Undo: $item")
    }

    override fun getBookmarks(): LiveData<List<BookMarkType>> {
        return boxStore.bookmark.query().asLiveData().map { items ->
            items.map { item ->
                when {
                    item.isMovie -> {
                        BookMarkType.Movie(item.movieDetail.target!!)

                    }
                    else -> {
                        BookMarkType.Tv(item.tvDetail.target!!)
                    }
                }
            }
        }
    }

    override fun bookState(id: Long): LiveData<Boolean> {
        return boxStore
            .bookmark
            .query()
            .equal(Bookmark_.movieId, id)
            .asLiveData()
            .map {
                it.firstOrNull() != null
            }
    }

    override fun getBookmark(id: Long): Bookmark? {
        return boxStore.bookmark[id]
    }

    private fun saveLatestRemoveItem(item: Bookmark) {
        val itemJson = JsonX.encodeToString(item)
        pref.edit {
            putString(LATEST_REMOVE_ID, itemJson)
        }
    }

    private fun getLatestRemoveItem(): Bookmark? {
        val itemJson = pref.getString(LATEST_REMOVE_ID, null) ?: return null
        return try {
            JsonX.decodeFromString(itemJson)
        } catch (e: Exception) {
            null
        }
    }

    private fun removeLatestRemoveItem() {
        pref.edit {
            remove(LATEST_REMOVE_ID)
        }
    }

    companion object {
        private const val LATEST_REMOVE_ID = "latest_remove_id"
    }
}