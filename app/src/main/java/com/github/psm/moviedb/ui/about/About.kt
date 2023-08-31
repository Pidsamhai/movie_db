package com.github.psm.moviedb.ui.about

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
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
    val context = LocalContext.current

    AboutPageContent(
        language = language,
        region = region,
        saveLanguage = { aboutViewModel.saveLanguage(it) },
        saveRegion = { aboutViewModel.saveRegion(it) },
        clearDataBase = {
            aboutViewModel.clearDataBase()
            Toast.makeText(context, "Clear success", Toast.LENGTH_SHORT).show()
        }
    )
}

@Composable
fun AboutPageContent(
    language: String?,
    region: String?,
    saveRegion: (region: String) -> Unit = { },
    saveLanguage: (language: String) -> Unit = { },
    clearDataBase: () -> Unit = { }
) {
    var showLanguageDialog by remember { mutableStateOf(false) }
    var showRegionDialog by remember { mutableStateOf(false) }
    var showConfirmDialog by remember { mutableStateOf(false) }
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
    if (showConfirmDialog) {
        AlertDialog(
            title = { Text(text = "Confirm clear database") },
            text = { Text(text = "All data will be lost. You can't undo it.") },
            onDismissRequest = { showConfirmDialog = false },
            dismissButton = {
                TextButton(
                    onClick = { showConfirmDialog = false }
                ) {
                    Text(text = "CANCEL")
                }           
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        clearDataBase()
                        showConfirmDialog = false
                    }
                ) {
                    Text(text = "OK")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top)
    ) {
        BaseAppBar(
            title = NavigationRoutes.About.label,
            scrollState = scrollState
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SettingItem(
                modifier = Modifier
                    .clickable { showLanguageDialog = !showLanguageDialog }
                    .fillMaxWidth(),
                title = "Language",
                value = language
            )

            SettingItem(
                modifier = Modifier
                    .clickable { showRegionDialog = !showRegionDialog }
                    .fillMaxWidth(),
                title = "Region",
                value = region
            )

            SettingItem(
                modifier = Modifier
                    .clickable {}
                    .fillMaxWidth()
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

            SettingItem(
                modifier = Modifier
                    .clickable {
                        context.startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("${BuildConfig.REPOSITORY}/releases")
                            )
                        )
                    }
                    .fillMaxWidth(),
                title = "Version",
                value = "%s(%s)".format(BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE)
            )

            SettingItem(
                modifier = Modifier
                    .clickable {
                        context.startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("${BuildConfig.REPOSITORY}/releases")
                            )
                        )
                    }
                    .fillMaxWidth(),
                title = "Build On",
                value = BuildConfig.BUILD_ON
            )

            SettingItem(
                modifier = Modifier
                    .clickable {
                        context.startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("${BuildConfig.REPOSITORY}/releases")
                            )
                        )
                    }
                    .fillMaxWidth(),
                title = "Build Date",
                value = BuildConfig.BUILD_DATE
            )

            SettingItem(
                modifier = Modifier
                    .clickable {
                        context.startActivity(
                            Intent(Intent.ACTION_VIEW, Uri.parse(BuildConfig.REPOSITORY))
                        )
                    }
                    .fillMaxWidth(),

            ) {
                Text(text = "Repository")
                Icon(painter = painterResource(id = R.drawable.ic_round_public), contentDescription = "public" )
            }

            SettingItem(
                modifier = Modifier
                    .clickable {
                        showConfirmDialog = true
                    }
                    .fillMaxWidth(),
            ) {
                Text(text = "Clear Database",
                    color = Color.Red,
                )
                Spacer(modifier = Modifier.size(8.dp))
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    tint = Color.Red
                )
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