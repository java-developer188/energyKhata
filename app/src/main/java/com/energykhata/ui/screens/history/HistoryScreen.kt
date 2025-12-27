package com.energykhata.ui.screens.history

import android.content.pm.ActivityInfo
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.runtime.remember
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
import com.energykhata.ui.theme.EnergyTeal
import com.energykhata.util.scaledFontSize
import com.energykhata.util.scaledIconSize
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
            contentScale = ContentScale.FillBounds,
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
                        modifier = Modifier.weight(0.1f)
                            .indication(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ),
                        onClick = { navController.navigateUp() }
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(scaledIconSize(35f, (35f * 0.85f), (35f * 0.75f)))
                                .indication(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ),
                            painter = painterResource(id = R.drawable.arrow_back),
                            contentDescription = "Back",
                            tint = EnergyTeal
                        )
                    }

                    Text(
                        modifier = Modifier.weight(0.9f),
                        text = meters.firstOrNull()?.title ?: "",
                        textAlign = TextAlign.Center,
                        color = EnergyTeal,
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        fontSize = scaledFontSize(32f,28f,26f)
                    )
                    IconButton(
                        modifier = Modifier.weight(0.1f)
                            .indication(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ),
                        onClick = {
                            navController.navigate(Screen.MAIN.route)
                            {
                                popUpTo(Screen.MAIN.route) {
                                    inclusive = true
                                }
                            }
                        },

                        ) {
                        Icon(
                            modifier = Modifier
                                .size(scaledIconSize(35f, (35f * 0.85f), (35f * 0.75f)))
                                .indication(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ),
                            painter = painterResource(id = R.drawable.home),
                            contentDescription = "Home",
                            tint = EnergyTeal
                        )
                    }
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier.padding(paddingValues)
            ) {
                meters.firstOrNull()?.let { meter ->
                    MeterReadingComponent(
                        viewModel = viewModel,
                        meter = meter,
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
