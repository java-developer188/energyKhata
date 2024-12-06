package com.energykhata.ui.screens.calcuation

import android.content.pm.ActivityInfo
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.History
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.energykhata.factory.MeterViewModelFactory
import com.energykhata.roomdb.models.Meter
import com.energykhata.roomdb.repositories.MeterRepository
import com.energykhata.roomdb.repositories.ReadingRepository
import com.energykhata.ui.LockScreenOrientation
import com.energykhata.ui.Screen
import com.energykhata.viewmodels.MeterViewModel

@Composable
fun CalculationScreen(
    navController: NavHostController,
    meterId: Int?,
    meterRepository: MeterRepository,
    readingRepository: ReadingRepository
) {
    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    val viewModel: MeterViewModel = viewModel(
        factory = MeterViewModelFactory(meterRepository, readingRepository)
    )

    val meters by viewModel.meters.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
//        Image(
//            painter = painterResource(id = R.drawable.bglite),
//            contentDescription = null,
//            contentScale = ContentScale.Fit,
//            modifier = Modifier.fillMaxSize(),
//            alpha = 0.6f
//        )

        Scaffold(
            topBar = {
                Row(
                    modifier = Modifier
                        .wrapContentHeight()
                        .padding(start = 15.dp,end=15.dp)
                        .fillMaxWidth()
                        .background(Color.Transparent),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (navController.previousBackStackEntry != null) {
                        IconButton(
                            modifier = Modifier.weight(0.1f),
                            onClick = { navController.navigateUp() }
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(25.dp),
                                imageVector = Icons.Default.ArrowBackIos, // Help icon
                                contentDescription = "Back",
                                tint = Color(0XFF00BCD4)
                            )
                        }
                    }
                    Text(
                        modifier = Modifier.weight(0.9f),
                        text = if(meters.isNotEmpty()) meters[0].title!! else "",
                        textAlign = TextAlign.Center,
                        color = Color(0XFF00BCD4),
                        style = MaterialTheme.typography.headlineMedium
                    )
                    IconButton(
                        modifier = Modifier.weight(0.1f),
                        onClick = { navController.navigate(Screen.HISTORY.route + "/" + meters[0].meterId)
                        }
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(25.dp),
                            imageVector = Icons.Default.History, // Help icon
                            contentDescription = "History",
                            tint = Color(0XFF00BCD4)
                        )
                    }
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier.padding(paddingValues)
            ) {
                PortraitLayout(meters, navController, viewModel)
            }
        }

    }
    LaunchedEffect(Unit) {
        if (meterId != null) {
            viewModel.getMeter(meterId)
        }
    }
}

@Composable
private fun PortraitLayout(
    meters: List<Meter>,
    navController: NavHostController,
    viewModel: MeterViewModel
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        userScrollEnabled = true
    )
    {
        items(meters.size) { i ->
            CalculationComponent(navController, viewModel, i, meters[i])
        }
    }
}