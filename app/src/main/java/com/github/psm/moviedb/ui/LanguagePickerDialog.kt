package com.github.psm.moviedb.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.github.psm.moviedb.R
import com.github.psm.moviedb.db.model.Country
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

sealed class LanguagePickerMode {
    data object LANGUAGE : LanguagePickerMode()
    data object REGION : LanguagePickerMode()
}

@Preview(showBackground = true)
@Composable
fun LanguagePickerDialog(
    onClose: () -> Unit = { },
    onSelected: (country: String) -> Unit = {},
    languagePickerMode: LanguagePickerMode = LanguagePickerMode.LANGUAGE
) {
    val resource = LocalContext.current.resources
    val str: String = resource.openRawResource(R.raw.languages).bufferedReader().readText()
    val languages = Json.decodeFromString<List<Country>>(str)
    val tile = if (languagePickerMode is LanguagePickerMode.LANGUAGE) "Language"
    else "Region"

    val onSelected: (language: String) -> Unit = {
        when (languagePickerMode) {
            LanguagePickerMode.LANGUAGE -> onSelected(it)
            LanguagePickerMode.REGION -> {
                onSelected(it.split("-").last().uppercase())
            }
        }
    }

    Dialog(
        onDismissRequest = onClose
    ) {
        Surface(
            modifier = Modifier
                .width(280.dp)
                .wrapContentHeight()
                .clip(RoundedCornerShape(16.dp))
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .padding(start = 24.dp, end = 24.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Select $tile",
                        style = MaterialTheme.typography.h6.copy(
                            fontSize = 21.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        textAlign = TextAlign.Start
                    )
                }

                LazyColumn(
                    modifier = Modifier
                        .height(400.dp)
                        .padding(start = 8.dp, end = 8.dp)
                ) {
                    items(languages) {
                        Column(
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                        ) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onSelected(it.code)
                                        onClose()
                                    }
                                    .padding(8.dp),
                                text = "${it.name} (${it.code})",
                                textAlign = TextAlign.Start
                            )
                            Divider(
                                thickness = 3.dp
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = onClose
                    ) {
                        Text(text = "Close")
                    }
                }
            }
        }
    }
}