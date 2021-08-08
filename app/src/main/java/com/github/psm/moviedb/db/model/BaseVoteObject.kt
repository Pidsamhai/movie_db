package com.github.psm.moviedb.db.model

import kotlinx.parcelize.IgnoredOnParcel
import kotlin.math.floor

interface BaseVoteObject {
    @IgnoredOnParcel
    val voteStar: VoteStar
}

class VoteStar(voteAverage: Double?) {
    val starCount: Int
    val hasHalf: Boolean
    val emptyStar: Int

    init {
        val realVote = (voteAverage ?: 0.0) / 2.0
        val _starCount = floor(realVote).toInt()
        val _hasHalf = (realVote - _starCount) >= 0.5 && realVote != MAX_STAR.toDouble()
        val _emptyStar = if (_hasHalf && _starCount == 4) 1 else MAX_STAR - _starCount
        starCount = _starCount
        hasHalf = _hasHalf
        emptyStar = if (hasHalf) _emptyStar - 1 else _emptyStar
    }

    companion object {
        const val MAX_STAR = 5
    }
}