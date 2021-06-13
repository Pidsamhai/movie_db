package com.github.psm.moviedb.repository

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
    private val boxStore: BoxStore
) : BookmarkRepository {

    override fun book(movieId: Long) {
        val bookmark = Bookmark(movieId = movieId)
        val movie = boxStore
            .movieDetail
            .query()
            .equal(MovieDetail_.id, movieId)
            .build()
            .findFirst()
        Timber.i(movie.toString())
        bookmark.movieDetail.target = movie
        boxStore.bookmark.put(bookmark)
    }

    override fun unBook(movieId: Long) {
        boxStore.bookmark.remove(movieId)
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
}