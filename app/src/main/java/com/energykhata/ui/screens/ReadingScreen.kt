package com.energykhata.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.energykhata.R
import com.energykhata.factory.ReadingViewModelFactory
import com.energykhata.roomdb.repositories.MeterRepository
import com.energykhata.roomdb.repositories.ReadingRepository
import com.energykhata.viewmodels.ReadingViewModel

@Composable
fun ReadingScreen(
    navController: NavHostController,
    meterId: Int?,
    meterRepository: MeterRepository,
    readingRepository: ReadingRepository
) {

    val viewModel: ReadingViewModel = viewModel(
        factory = ReadingViewModelFactory(meterRepository, readingRepository)
    )

            val readings by viewModel.readings.collectAsState()

            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.bglite),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxSize(),
                    alpha = 0.6f
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally, // Center horizontally
                    verticalArrangement = Arrangement.SpaceEvenly // Center vertically
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally, // Center horizontally
                        verticalArrangement = Arrangement.Center // Center vertically
                    ) {
                        Text(
                            text = "Welcome to Energy Khata!",
                            style = MaterialTheme.typography.headlineLarge, // Big heading
                            textAlign = TextAlign.Center // Center the text
                        )
                        Spacer(modifier = Modifier.height(8.dp)) // Add space between heading and text
                        Text(
                            text = "Easily manage and store readings of your energy meters. Tap the Help button anytime for guidance on using the app.",
                            style = MaterialTheme.typography.bodyLarge, // Regular text style
                            textAlign = TextAlign.Center // Center the text
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    LazyColumn(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(10.dp),
                        verticalArrangement = Arrangement.SpaceBetween,
                        userScrollEnabled = true)
                    {
                        items(readings.size) { i ->
                            ElevatedButton(
                                modifier = Modifier.fillMaxWidth().padding(20.dp),
                                onClick = { }) {
                                Text("Meter "+readings[i].date.toString()+""+readings[i].reading,
                                    style = MaterialTheme.typography.headlineSmall, // Big heading
                                    textAlign = TextAlign.Center // Center the text
                                )
                            }
                        }
                    }
                }

            }
    LaunchedEffect(Unit) {
        if (meterId != null) {
            viewModel.getReadings(meterId)
        }
    }
}