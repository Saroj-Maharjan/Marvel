package com.sawrose.marvelapp.core.designsystem.component

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.sawrose.marvelapp.core.designsystem.theme.MarvelTheme

@Composable
fun MarvelLoadingWheel(
    contentDesc: String,
    modifier: Modifier = Modifier,
) {
    val infiniteTransaction = rememberInfiniteTransition()

    // specifying float animations for slow drawing of lines
    val startValue = if(LocalInspectionMode.current) 0F else 1F
    val floatAnimValues = (0 until NUM_OF_LINES).map {
        remember{ Animatable(startValue) }
    }

    LaunchedEffect(floatAnimValues){
        (0 until NUM_OF_LINES).map { index ->
            floatAnimValues[index].animateTo(
                targetValue = 0F,
                animationSpec = tween(
                    durationMillis = 100,
                    easing = FastOutSlowInEasing,
                    delayMillis = 40 * index
                )
            )
        }
    }

    //specifing the rotation animation for the entire canvas
    val rotationAnimValue = infiniteTransaction.animateFloat(
        initialValue = 0F,
        targetValue = 360F,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = ROTATION_TIME,
                easing = LinearEasing
            ),
        )
    )

    // specifing the base color animation for progress lines
    val baseLineColor = MaterialTheme.colorScheme.onBackground
    val progressLineColor = MaterialTheme.colorScheme.inversePrimary

    val colorAnimValue = (0 until NUM_OF_LINES).map {index->
        infiniteTransaction.animateColor(
            initialValue = baseLineColor,
            targetValue = progressLineColor,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = 100 / 2
                    progressLineColor at ROTATION_TIME / NUM_OF_LINES / 2 with LinearEasing
                    baseLineColor at ROTATION_TIME / NUM_OF_LINES with LinearEasing
                },
                repeatMode = RepeatMode.Restart,
                initialStartOffset = StartOffset(ROTATION_TIME / NUM_OF_LINES/2 * index )
            )
        )
    }


    // Draws out the LoadingWheel Canvas composable and sets the animations
    Canvas(
        modifier = modifier
            .size(48.dp)
            .padding(8.dp)
            .graphicsLayer { rotationZ = rotationAnimValue.value }
            .semantics { contentDescription = contentDesc }
            .testTag("MarvelLoadingWheel")
    ){
        repeat(NUM_OF_LINES){index->
            rotate(degrees = index * 360f){
                drawLine(
                    color = colorAnimValue[index].value,
                    // Animates the initially drawn 1 pixel alpha from 0 to 1
                    alpha = if (floatAnimValues[index].value < 1f) 1f else 0f,
                    strokeWidth = 4F,
                    cap = StrokeCap.Round,
                    start = Offset(size.width / 2, size.height / 4),
                    end = Offset(size.width / 2, floatAnimValues[index].value * size.height / 4),
                )
            }
        }
    }
}


@ThemePreviews
@Composable
fun MarvelLoadingWheelPreview() {
    MarvelTheme {
        Surface {
            MarvelLoadingWheel(contentDesc = "LoadingWheel")
        }
    }
}

private const val ROTATION_TIME = 12000
private const val NUM_OF_LINES = 12