package com.github.psm.moviedb.ui.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun NormalTextField(
    modifier: Modifier,
    value: TextFieldValue,
    onValueChange: (value: TextFieldValue) -> Unit,
    placeholder: String = ""
) {
    Column {
        Surface() {
            BasicTextField(
                modifier = modifier,
                value = value,
                onValueChange = onValueChange,
                textStyle = TextStyle.Default.copy(fontSize = 18.sp)
            )
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = if (value.text.isEmpty()) placeholder else "",
                style = TextStyle
                    .Default
                    .copy(
                        fontSize = 18.sp,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
                    )
            )
        }
        Divider(Modifier.fillMaxWidth(),color = Color.Black, thickness = 2.dp)
    }
}

@Composable
fun NormalTextField(
    modifier: Modifier,
    value: String,
    onValueChange: (value: String) -> Unit,
    placeholder: String = ""
) {
    var underLineAlpha by remember { mutableStateOf(0.5f) }
    Column {
        Surface() {
            BasicTextField(
                modifier = modifier
                    .onFocusChanged { focusState ->
                        underLineAlpha = if (focusState.isFocused) 1f
                        else 0.5f
                    },
                value = value,
                onValueChange = onValueChange,
                textStyle = TextStyle.Default.copy(fontSize = 18.sp)
            )
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = if (value.isEmpty()) placeholder else "",
                style = TextStyle
                    .Default
                    .copy(
                        fontSize = 18.sp,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
                    )
            )
        }
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colors.onSurface.copy(alpha = underLineAlpha),
            thickness = 2.dp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NormalTextFieldPreview() {
    NormalTextField(
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth(),
        value = "Placeholder",
        onValueChange = {  }
    )
}