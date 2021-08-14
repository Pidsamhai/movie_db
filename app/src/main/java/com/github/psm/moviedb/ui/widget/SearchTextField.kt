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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val SearchTextFieldBackground: Color
    @Composable get() = MaterialTheme.colors.onSurface.copy(alpha = 0.1f)

private val SearchTextFieldTextStyle: TextStyle
    @Composable get() = TextStyle.Default.copy(
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colors.onSurface
    )


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
    value: String = "",
    onValueChange: (value: String) -> Unit,
    placeholder: String = "",
    backGroundColor: Color = SearchTextFieldBackground
) {

    var textValueState by remember {
        mutableStateOf(
            TextFieldValue(
                text = value,
                selection = TextRange(index = value.length)
            )
        )
    }

    val textValue = textValueState.copy(text = value)

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(backGroundColor)
            .padding(8.dp),
        contentAlignment = Alignment.CenterStart

    ) {
        BasicTextField(
            modifier = Modifier
                .then(modifier)
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            value = textValue,
            onValueChange = {
                textValueState = it
                if (value != it.text) {
                    onValueChange(it.text)
                }
            },
            textStyle = SearchTextFieldTextStyle,
            cursorBrush = SolidColor(MaterialTheme.colors.onSurface),
            maxLines = 1
        )
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = if (value.isEmpty()) placeholder else "",
            style = SearchTextFieldTextStyle,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
        )
    }
}

@Preview(showBackground = true, device = Devices.DEFAULT)
@Composable
fun NormalTextFieldPreview() {
    SearchTextField(
        modifier = Modifier
            .fillMaxWidth(),
        placeholder = "Placeholder",
        value = "Hello",
        onValueChange = { }
    )
}