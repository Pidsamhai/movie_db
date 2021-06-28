package com.github.psm.moviedb.ui.widget

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


/**
 * 0.0 to 1
 */
@Composable
fun Gauge(
    modifier: Modifier = Modifier,
    value: Float = 0f,
    bgColor: Color = Color.White,
    fontSize: TextUnit = 16.sp,
    strokeSize: Float = 16f,
    animated: Boolean = false
) {
    var guardValue by remember { mutableStateOf(0f) }

    val animGuardState = animateFloatAsState(
        targetValue = guardValue,
        animationSpec = tween(3000)
    )

    var textValue by remember { mutableStateOf(value.toInt()) }

    val animTextSate = animateIntAsState(
        targetValue = textValue,
        animationSpec = tween(3000)
    )


    val color = when {
        value < 0.5f -> Color.Red
        value < 0.7f -> Color.Yellow
        else -> Color.Green
    }

    val finalGuardValue = value * 360f
    val finalTextValue = (value * 100).toInt()

    LaunchedEffect(
        key1 = value,
        block = {
            if (animated) {
                guardValue = finalGuardValue
                textValue = finalTextValue
            }
        }
    )

    Surface(
        modifier
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(bgColor)
                .padding(4.dp)
        ) {
            drawArc(
                startAngle = 270f,
                sweepAngle = if (animated) animGuardState.value else finalGuardValue,
                useCenter = false,
                color = color,
                style = Stroke(strokeSize, cap = StrokeCap.Round)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (animated) "${animTextSate.value}%" else "$finalTextValue%",
                fontSize = fontSize,
                fontWeight = FontWeight.ExtraBold,
                color = color
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GaugePreview() {
    Gauge(
        modifier = Modifier
            .size(200.dp)
            .padding(16.dp)
            .clip(CircleShape),
        value = 1f,
        bgColor = Color.Black.copy(alpha = 0.4f)
    )
}