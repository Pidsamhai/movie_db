package com.github.psm.moviedb.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun SearchTextField(
    modifier: Modifier,
    value: TextFieldValue,
    onValueChange: (value: TextFieldValue) -> Unit,
    placeholder: String = ""
) {
    Column {
        Surface {
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
        Divider(Modifier.fillMaxWidth(), color = Color.Black, thickness = 2.dp)
    }
}

@Composable
fun SearchTextField(
    modifier: Modifier,
    initialValue: String = "",
    onValueChange: (value: String) -> Unit,
    placeholder: String = "",
    backGroundColor: Color = Color(0xFFEEEEEE)
) {
    var textValue by remember { mutableStateOf(initialValue) }
    val textStyle = remember { TextStyle.Default.copy(fontWeight = FontWeight.Bold) }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(backGroundColor)
            .padding(8.dp),
        contentAlignment = Alignment.CenterStart

    ) {
        BasicTextField(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 8.dp),
            value = textValue,
            onValueChange = {
                textValue = it
                onValueChange(it)
            },
            textStyle = textStyle,
            maxLines = 1,
        )
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = if (textValue.isEmpty()) placeholder else "",
            style = textStyle,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NormalTextFieldPreview() {
    SearchTextField(
        modifier = Modifier
            .fillMaxWidth(),
        placeholder = "Placeholder",
        initialValue = "Hello",
        onValueChange = { }
    )
}