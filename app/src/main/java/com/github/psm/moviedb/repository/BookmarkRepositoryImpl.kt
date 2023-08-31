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
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import timber.log.Timber
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val boxStore: BoxStore,
    private val pref: SharedPreferences
) : BookmarkRepository {

    override fun book(id: Long, isMovie: Boolean) {
        val exist = boxStore
            .bookmark
            .query()
            .equal(Bookmark_.id, id)
            .equal(Bookmark_.isMovie, isMovie)
            .build()
            .findFirst()
        if (exist != null) return
        val bookmark = Bookmark(id = id, isMovie = isMovie)
        bookmark.movieDetail.target = boxStore.movieDetail[id]
        bookmark.tvDetail.target = boxStore.tvDetail[id]
        boxStore.bookmark.put(bookmark)
    }

    override fun unBook(id: Long, isMovie: Boolean) {
        val item = boxStore.bookmark
            .query()
            .equal(Bookmark_.id, id)
            .equal(Bookmark_.isMovie, isMovie)
            .build()
            .findFirst() ?: return
        saveLatestRemoveItem(item)
        boxStore.bookmark.remove(item)
    }

    override fun undoUnBookmark() {
        val item = getLatestRemoveItem() ?: return
        book(item.id ?: item.uid, item.isMovie)
        removeLatestRemoveItem()
        Timber.i("Undo: $item")
    }

    override fun getBookmarks(): LiveData<List<BookMarkType>> {
        return boxStore.bookmark.query().asLiveData().map { items ->
            items.map { item ->
                Timber.d(item.toString())
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

    override fun bookState(id: Long, isMovie: Boolean): LiveData<Boolean> {
        return boxStore
            .bookmark
            .query()
            .equal(Bookmark_.id, id)
            .equal(Bookmark_.isMovie, isMovie)
            .asLiveData()
            .map {
                it.firstOrNull() != null
            }
    }

    override fun getBookmark(id: Long): Bookmark? {
        return boxStore.bookmark.query().equal(Bookmark_.id, id).build().findFirst()
    }

    private fun saveLatestRemoveItem(item: Bookmark) {
        val itemJson = JsonX.encodeToString(item)
        pref.edit {
            putString(LATEST_REMOVE_ID, itemJson)
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
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