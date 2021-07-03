package com.github.psm.moviedb.ui.about

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatDelegate.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.psm.moviedb.BuildConfig
import com.github.psm.moviedb.NavigationRoutes
import com.github.psm.moviedb.R
import com.github.psm.moviedb.ui.LanguagePickerDialog
import com.github.psm.moviedb.ui.LanguagePickerMode
import com.github.psm.moviedb.ui.widget.BaseAppBar
import com.github.psm.moviedb.ui.widget.SettingItem

@Composable
fun About(
    aboutViewModel: AboutViewModel = hiltViewModel()
) {
    val language by aboutViewModel.language.observeAsState()
    val region by aboutViewModel.region.observeAsState()
    AboutPageContent(
        language = language,
        region = region,
        saveLanguage = { aboutViewModel.saveLanguage(it) },
        saveRegion = { aboutViewModel.saveRegion(it) },
    )
}

@Composable
fun AboutPageContent(
    language: String?,
    region: String?,
    saveRegion: (region: String) -> Unit = { },
    saveLanguage: (language: String) -> Unit = { },
) {
    var showLanguageDialog by remember { mutableStateOf(false) }
    var showRegionDialog by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    if (showLanguageDialog) {
        LanguagePickerDialog(
            onClose = { showLanguageDialog = false },
            onSelected = { saveLanguage(it) },
            languagePickerMode = LanguagePickerMode.LANGUAGE
        )
    }
    if (showRegionDialog) {
        LanguagePickerDialog(
            onClose = { showRegionDialog = false },
            onSelected = { saveRegion(it) },
            languagePickerMode = LanguagePickerMode.REGION
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top)
    ) {
        BaseAppBar(
            title = NavigationRoutes.About.label,
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

            SettingItem(
                modifier = Modifier
                    .clickable {}
                    .size(100.dp)
            ) {
                val iconRes: Int = when(getDefaultNightMode()) {
                    MODE_NIGHT_YES -> R.drawable.ic_round_dark_mode
                    MODE_NIGHT_FOLLOW_SYSTEM, MODE_NIGHT_UNSPECIFIED -> R.drawable.ic_round_settings
                    MODE_NIGHT_NO -> R.drawable.ic_round_light_mode
                    else -> R.drawable.ic_round_settings
                }
                Text(text = "Dark mode")
                Spacer(modifier = Modifier.size(8.dp))
                Icon(
                    painter = painterResource(id = iconRes), 
                    contentDescription = null
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            SettingItem(
                modifier = Modifier
                    .clickable {
                        context.startActivity(
                            Intent(Intent.ACTION_VIEW, Uri.parse(BuildConfig.REPOSITORY))
                        )
                    }
                    .size(100.dp),
             ) {
                Icon(
                    modifier = Modifier.size(80.dp),
                    painter = painterResource(id = R.drawable.ic_github_logo),
                    contentDescription = null
                )
            }
            SettingItem(
                modifier = Modifier
                    .clickable {
                        context.startActivity(
                            Intent(Intent.ACTION_VIEW, Uri.parse("${BuildConfig.REPOSITORY}/releases"))
                        )
                    }
                    .size(100.dp),
                title = "Version",
                value = BuildConfig.VERSION_NAME
            )
            repeat(1) {
                Box(modifier = Modifier.size(100.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AboutPreview() {
    AboutPageContent(
        region = "en-Us",
        language = "US"
    )
}