package com.energykhata.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun scaledFontSize(large: Float, medium: Float, small: Float): TextUnit {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp // Screen width in dp

    return when {
        screenWidth > 600 -> large.sp // Large screens
        screenWidth > 360 -> medium.sp // Medium screens
        else -> small.sp // Small screens
    }
}

@Composable
fun scaledIconSize(large: Float , medium : Float , small : Float): Dp {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp // Screen width in dp

    return when {
        screenWidth > 600 -> large.dp // Large screens
        screenWidth > 360 -> medium.dp // Medium screens
        else -> small.dp // Small screens
    }
}