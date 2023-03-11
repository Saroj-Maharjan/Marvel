package com.sawrose.marvelapp.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/**
* A class to model background color and tonal elevation values for Marvel app.
*/
@Immutable
data class TintTheme(
    val iconTint: Color? = null,
)

/**
 * A composition local for [TintTheme].
 */
val LocalTintTheme = staticCompositionLocalOf { TintTheme() }