package com.energykhata.ui.screens.splash

import android.content.pm.ActivityInfo
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.energykhata.R
import com.energykhata.ui.LockScreenOrientation
import com.energykhata.ui.Screen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    // State to manage the display time
    var isSplashDisplayed by remember { mutableStateOf(true) }
    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    // Delay for 2 seconds
    LaunchedEffect(Unit) {
        delay(4000L)
        isSplashDisplayed = false
        navController.navigate(Screen.MAIN.route ) {
            popUpTo(Screen.SPLASH.route ) { inclusive = true }
        }
    }

    // UI for the splash screen
    if (isSplashDisplayed) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
//            Text(
//                text = "Welcome to Energy Khata",
//                style = MaterialTheme.typography.headlineLarge,
//                textAlign = TextAlign.Center
//            )
            Image(
            painter = painterResource(id = R.drawable.splash),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )
        }
    }
}
