package com.github.psm.moviedb.ui.about

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
import com.github.psm.moviedb.ui.LanguagePickerDialog

@Composable
fun About(
    aboutViewModel: AboutViewModel = hiltViewModel()
) {

    val language by aboutViewModel.language.observeAsState()
    val region by aboutViewModel.region.observeAsState()
    var showLanguageDialog by remember { mutableStateOf(false) }
    var showRegionDialog by remember { mutableStateOf(false) }
    var languageCode by remember { mutableStateOf("") }
    var regionCode by remember { mutableStateOf("") }

    if (languageCode.isNotEmpty() && language != null) {
        aboutViewModel.saveLanguage(languageCode)
        languageCode = ""
    }

    if (regionCode.isNotEmpty() && region != null) {
        val _languageCode = regionCode.split("-")
        aboutViewModel.saveRegion(_languageCode.last().uppercase())
        regionCode = ""
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "language :${language} region: $region")
        if (showLanguageDialog) {
            LanguagePickerDialog(
                onClose = { showLanguageDialog = false },
                onSelected = { languageCode = it }
            )
        }
        if (showRegionDialog) {
            LanguagePickerDialog(
                onClose = { showRegionDialog = false },
                onSelected = { regionCode = it }
            )
        }
        Button(
            onClick = { showLanguageDialog = !showLanguageDialog }
        ) {
            Text(text = "Change Language")
        }

        Button(
            onClick = { showRegionDialog = !showRegionDialog }
        ) {
            Text(text = "Change Region")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AboutPreview() {
    About()
}