package com.github.psm.moviedb.ui.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.ElevatedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.github.psm.moviedb.R

@Composable
fun ErrorContent(
    onRetry: () -> Unit = {}
) {
    return Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Some thing went wrong.")
        Text(text = "Please try again")
        Spacer(modifier = Modifier.height(8.dp))
        ElevatedButton(onClick = onRetry) {
            Icon(painter = painterResource(id = R.drawable.ic_round_refresh), contentDescription = "refresh")
            Text(text = "Retry")
        }
    }
}