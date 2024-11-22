package com.energykhata.ui.screens

import androidx.activity.compose.BackHandler
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
import com.energykhata.factory.MainViewModelFactory
import com.energykhata.roomdb.repositories.MeterRepository
import com.energykhata.roomdb.repositories.UserRepository
import com.energykhata.viewmodels.MainViewModel

@Composable
fun MainScreen(
    navController: NavHostController,
    meterRepository: MeterRepository,
    userRepository: UserRepository
) {
    BackHandler {
        if (navController.previousBackStackEntry != null) {
            navController.popBackStack() // Navigate back in the stack
        }
    }
    val viewModel: MainViewModel = viewModel(
        factory = MainViewModelFactory(meterRepository,userRepository)
    )

            val meters by viewModel.meters.collectAsState()

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
                        items(meters.size) { i ->
                            ElevatedButton(
                                modifier = Modifier.fillMaxWidth().padding(20.dp),
                                onClick = { navController.navigate(Screen.METERSCREEN.route+"/"+(i+1)) }) {
                                Text("Meter "+(i+1),
                                    style = MaterialTheme.typography.headlineSmall, // Big heading
                                    textAlign = TextAlign.Center // Center the text
                                )
                            }
                        }
                    }
                }

            }
    LaunchedEffect(Unit) {
        viewModel.getMeters()
    }
}