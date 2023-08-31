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
        val starCount = floor(realVote).toInt()
        val hasHalf = (realVote - starCount) >= 0.5 && realVote != MAX_STAR.toDouble()
        val emptyStar = if (hasHalf && starCount == 4) 1 else MAX_STAR - starCount
        this.starCount = starCount
        this.hasHalf = hasHalf
        this.emptyStar = if (this.hasHalf) emptyStar - 1 else emptyStar
    }

    companion object {
        const val MAX_STAR = 5
    }
}