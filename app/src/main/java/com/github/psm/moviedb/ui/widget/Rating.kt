package com.github.psm.moviedb.ui.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.psm.moviedb.R
import com.github.psm.moviedb.db.model.VoteStar
import com.github.psm.moviedb.ui.theme.Stared

@Composable
fun Rating(
    modifier: Modifier = Modifier,
    voteStar: VoteStar
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start
    ) {
        repeat(voteStar.starCount) {
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(id = R.drawable.ic_star),
                contentDescription = null,
                tint = Stared
            )
        }
        if (voteStar.hasHalf) {
            Box {
                Icon(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(id = R.drawable.ic_left_half_star),
                    contentDescription = null,
                    tint = Stared
                )
                Icon(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(id = R.drawable.ic_right_half_star),
                    contentDescription = null,
                    tint = LocalContentColor.current.copy(alpha = 0.17f)
                )
            }
        }
        repeat(voteStar.emptyStar) {
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(id = R.drawable.ic_star),
                contentDescription = null,
                tint = LocalContentColor.current.copy(alpha = 0.17f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RatingPreview() {
    Rating(
        voteStar = VoteStar(6.5)
    )
}