package com.github.psm.moviedb.ui.widget.movie

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.psm.moviedb.db.model.shared.credit.Cast
import com.github.psm.moviedb.ui.widget.Image
import com.github.psm.moviedb.utils.toImgUrl

@Composable
fun CastingItem(
    modifier: Modifier = Modifier,
    onCastClick: ((id: Long) -> Unit)? = null,
    cast: Cast
) {
    val contentModifier = if (onCastClick != null)
        Modifier.fillMaxWidth().clickable { onCastClick(cast.id ?: return@clickable) }
    else
        Modifier.fillMaxWidth()

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        elevation = 8.dp
    ) {
        Column(
            modifier = contentModifier
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2f / 3),
                data = cast.profilePath?.toImgUrl(),
                contentScale = ContentScale.FillBounds,
                fadeIn = true,
                fadeInDurationMs = 2000,
                enablePlaceHolder = true
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = cast.name ?: "",
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onSurface
                )

                Text(
                    text = cast.character ?: "",
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onSurface,
                    fontSize = 10.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CastingItemPreview() {
    CastingItem(
        modifier = Modifier.width(200.dp),
        onCastClick = {  },
        cast = Cast(
            name = "Name LastName",
            character = "Character"
        )
    )
}