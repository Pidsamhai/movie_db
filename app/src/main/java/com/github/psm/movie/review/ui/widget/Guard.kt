package com.github.psm.movie.review.ui.widget

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.launch
import java.util.*
import kotlin.concurrent.schedule


/**
 * 0.0 to 1
 */
@Composable
fun Guard(
    modifier: Modifier = Modifier,
    value: Float = 0f,
    bgColor: Color = Color.White,
    fontSize: TextUnit =  16.sp,
    strokeSize: Float = 16f,
    animated: Boolean = false
) {
    val coroutine = rememberCoroutineScope()
    val anim = remember { mutableStateOf(0f) }
    val animState = animateFloatAsState(
        targetValue = anim.value,
        animationSpec = tween(3000)
    )

    val color = when {
        value < 0.5f -> Color.Red
        value < 0.7f -> Color.Yellow
        else -> Color.Green
    }

    val finalValue = value * 360f

    if (animated) {
        Timer("SettingUp", false).schedule(1000) {
            coroutine.launch {
                anim.value = finalValue
            }
        }

    }

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
                sweepAngle = if (animated) animState.value else finalValue,
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
                text = "${(value * 100).toInt()}%",
                fontSize = fontSize,
                fontWeight = FontWeight.ExtraBold,
                color = color
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GuardPreview() {
    Guard(
        modifier = Modifier
            .size(200.dp)
            .padding(16.dp)
            .clip(CircleShape),
        value = 1f,
        bgColor = Color.Black.copy(alpha = 0.4f)
    )
}