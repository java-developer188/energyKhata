package com.energykhata.ui.screens.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.energykhata.ui.Screen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    // State to manage the display time
    var isSplashDisplayed by remember { mutableStateOf(true) }

    // Delay for 2 seconds
    LaunchedEffect(Unit) {
        delay(2000L)
        isSplashDisplayed = false
        navController.navigate(Screen.MAIN.route ) {
            popUpTo(Screen.SPLASH.route ) { inclusive = true }
        }
    }

    // UI for the splash screen
    if (isSplashDisplayed) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Welcome to Energy Khata",
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}
