package com.github.psm.moviedb.ui.about

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.psm.moviedb.ui.LanguagePickerDialog
import com.github.psm.moviedb.ui.widget.BaseAppBar
import com.github.psm.moviedb.ui.widget.SettingItem

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
    val scrollState = rememberScrollState()

    if (languageCode.isNotEmpty() && language != null) {
        aboutViewModel.saveLanguage(languageCode)
        languageCode = ""
    }

    if (regionCode.isNotEmpty() && region != null) {
        val _languageCode = regionCode.split("-")
        aboutViewModel.saveRegion(_languageCode.last().uppercase())
        regionCode = ""
    }

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        BaseAppBar(
            centerContent = {
                Text(text = "About", fontWeight = FontWeight.Bold)
            },
            scrollState = scrollState
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            SettingItem(
                modifier = Modifier
                    .clickable { showLanguageDialog = !showLanguageDialog }
                    .size(100.dp),
                title = "Language",
                value = language
            )

            SettingItem(
                modifier = Modifier
                    .clickable { showRegionDialog = !showRegionDialog }
                    .size(100.dp),
                title = "Region",
                value = region
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AboutPreview() {
    About()
}