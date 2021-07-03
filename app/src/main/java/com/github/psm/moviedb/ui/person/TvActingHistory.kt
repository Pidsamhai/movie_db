package com.github.psm.moviedb.ui.person

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.psm.moviedb.db.model.person.tv.PersonTvCredit
import com.github.psm.moviedb.ui.widget.ActingHistoryItem

@Composable
fun TvActingHistory(
    personTvCredit: PersonTvCredit?
) {
    val sortCasts = personTvCredit?.tvCast?.sortedByDescending { it.firstAirDate }
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        sortCasts?.let { cast ->
            items(cast) {
                ActingHistoryItem(tvCast = it)
            }
        }
    }
}