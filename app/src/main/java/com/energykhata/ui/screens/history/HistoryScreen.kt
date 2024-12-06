package com.energykhata.ui.screens.history

import android.content.pm.ActivityInfo
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Divider
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
import com.energykhata.factory.ReadingViewModelFactory
import com.energykhata.roomdb.models.Reading
import com.energykhata.roomdb.repositories.MeterRepository
import com.energykhata.roomdb.repositories.ReadingRepository
import com.energykhata.ui.LockScreenOrientation
import com.energykhata.ui.Screen
import com.energykhata.viewmodels.ReadingViewModel

@Composable
fun HistoryScreen(
    navController: NavHostController,
    meterId: Int?,
    meterRepository: MeterRepository,
    readingRepository: ReadingRepository
) {
    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    val viewModel: ReadingViewModel = viewModel(
        factory = ReadingViewModelFactory(meterRepository, readingRepository)
    )

    val readings by viewModel.readings.collectAsState()
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
                        onClick = {navController.navigate(Screen.MAIN.route )},

                    ) {
                        Icon(
                            modifier = Modifier
                                .size(25.dp),
                            imageVector = Icons.Default.Home, // Help icon
                            contentDescription = "Home",
                            tint = Color(0XFF00BCD4)
                        )
                    }
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier.padding(paddingValues)
            ) {
                    MeterReadingScreen(
                        meterName = if(meters.isNotEmpty()) meters[0].title!! else " " ,
                        readings = readings) {}
            }
        }

        //OldReadingScreen(meterId, readings)

    }
    LaunchedEffect(Unit) {
        if (meterId != null) {
            viewModel.getReadings(meterId)
            viewModel.getMeter(meterId)
        }
    }
}

@Composable
private fun OldReadingScreen(
    meterId: Int?,
    readings: List<Reading>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(2.dp),
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            text = "Meter $meterId",
            style = MaterialTheme.typography.headlineLarge, // Big heading
            textAlign = TextAlign.Center // Center the text
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .border(1.dp, Color(0XFF00668b), CutCornerShape(5.dp))
                .background(Color(0xFFADD8E7))
                .padding(10.dp)
        ) {
            Text(
                modifier = Modifier.weight(0.5f),
                text = "Reading",
                style = MaterialTheme.typography.headlineSmall, // Big heading
                textAlign = TextAlign.Center // Center the text
            )
            Divider(modifier = Modifier.width(2.dp))
            Text(
                modifier = Modifier.weight(0.5f),
                text = "Date & Time",
                style = MaterialTheme.typography.headlineSmall, // Regular text style
                textAlign = TextAlign.Center // Center the text
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Divider(modifier = Modifier.height(2.dp))
        Spacer(modifier = Modifier.height(10.dp))
        LazyColumn(
            modifier = Modifier
                .wrapContentSize(),
            userScrollEnabled = true
        )
        {
            items(readings.size) { i ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(
                            if (i % 2 == 0) {
                                Color.White
                            } else {
                                Color.LightGray
                            }
                        )
                        .border(1.dp, Color.Gray, CutCornerShape(5.dp))
                        .padding(5.dp)
                ) {
                    Text(
                        modifier = Modifier.weight(0.5f),
                        text = readings[i].reading.toString(),
                        style = MaterialTheme.typography.headlineSmall, // Big heading
                        textAlign = TextAlign.Center // Center the text
                    )
                    Divider(modifier = Modifier.width(2.dp))
                    Text(
                        modifier = Modifier.weight(0.5f),
                        text = readings[i].date,
                        style = MaterialTheme.typography.headlineSmall, // Regular text style
                        textAlign = TextAlign.Center // Center the text
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
            }
        }
    }
}