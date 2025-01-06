package com.energykhata.ui.screens.history

import android.content.pm.ActivityInfo
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.energykhata.R
import com.energykhata.factory.ReadingViewModelFactory
import com.energykhata.roomdb.repositories.MeterRepository
import com.energykhata.roomdb.repositories.ReadingRepository
import com.energykhata.ui.LockScreenOrientation
import com.energykhata.ui.Screen
import com.energykhata.viewmodels.ReadingViewModel

@Composable
fun HistoryScreen(
    navController: NavHostController,
    meterId: Long?,
    meterRepository: MeterRepository,
    readingRepository: ReadingRepository,
) {
    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    val viewModel: ReadingViewModel = viewModel(
        factory = ReadingViewModelFactory(meterRepository, readingRepository)
    )

    val readings by viewModel.readings.collectAsState()
    val meters by viewModel.meters.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.bgpattern),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize(),
            alpha = 0.5f
        )
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                Row(
                    modifier = Modifier
                        .wrapContentHeight()
                        .padding(10.dp)
                        .fillMaxWidth()
                        .background(Color.Transparent),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    IconButton(
                        modifier = Modifier.weight(0.1f),
                        onClick = { navController.navigateUp() }
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(35.dp),
                            painter = painterResource(id = R.drawable.arrow_back), // Help icon
                            contentDescription = "Back",
                            tint = Color(0XFF008D9F)
                        )
                    }

                    Text(
                        modifier = Modifier.weight(0.9f),
                        text = if (meters.isNotEmpty()) meters[0].title!! else "",
                        textAlign = TextAlign.Center,
                        color = Color(0XFF008D9F),
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.ExtraBold
                    )
                    IconButton(
                        modifier = Modifier.weight(0.1f),
                        onClick = {
                            navController.navigate(Screen.MAIN.route)
                            {
                                popUpTo(Screen.MAIN.route) {
                                    inclusive = true
                                } // Clear the back stack
                            }
                        },

                        ) {
                        Icon(
                            modifier = Modifier
                                .size(35.dp),
                            painter = painterResource(id = R.drawable.home),
                            contentDescription = "Home",
                            tint = Color(0XFF008D9F)
                        )
                    }
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier.padding(paddingValues)
            ) {
                if (meters.isNotEmpty()) {
                    MeterReadingScreen(
                        viewModel = viewModel,
                        meter = meters[0],
                        readings = readings
                    )
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        if (meterId != null) {
            viewModel.getReadings(meterId)
            viewModel.getMeter(meterId)
        }
    }
}