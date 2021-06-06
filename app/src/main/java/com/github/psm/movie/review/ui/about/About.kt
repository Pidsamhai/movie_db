package com.github.psm.movie.review.ui.about

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.psm.movie.review.ui.LanguagePickerDialog

@Composable
fun About(
    aboutViewModel: AboutViewModel = hiltViewModel()
) {

    val language by aboutViewModel.language.observeAsState()
    var showDialog by remember { mutableStateOf(false) }
    var regionCode by remember { mutableStateOf("") }

    if (regionCode.isNotEmpty() && language != null) {
        aboutViewModel.saveLanguage(regionCode)
        regionCode = ""
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "language :${language}")
        if (showDialog) {
            LanguagePickerDialog(
                onClose = { showDialog = false },
                onSelected = { regionCode = it }
            )
        }
        Button(
            onClick = { showDialog = !showDialog }
        ) {
            Text(text = "Change Language")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AboutPreview() {
    About()
}