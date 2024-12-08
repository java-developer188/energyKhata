package com.energykhata.ui.screens.calcuation

import android.content.pm.ActivityInfo
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.CoroutineScope

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
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
//        Image(
//            painter = painterResource(id = R.drawable.bglite),
//            contentDescription = null,
//            contentScale = ContentScale.Fit,
//            modifier = Modifier.fillMaxSize(),
//            alpha = 0.6f
//        )

        Scaffold(
            snackbarHost = {
                SnackbarHost(
                    hostState = snackbarHostState,
                    snackbar = { data ->
                        Box(modifier = Modifier.padding(10.dp))
                        Snackbar(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(5.dp),
                            shape = RoundedCornerShape(10.dp),
                            containerColor = Color(0XFF00BCD4),
                            contentColor = Color(0XFFFFF9E6)
                        ) {
                            Row(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .background(Color(0XFF00BCD4)),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween

                            ) {
                                Icon(
                                    imageVector = Icons.Default.Warning,
                                    contentDescription = null,
                                    tint = Color(0XFFff9966)
                                )
                                Spacer(modifier = Modifier.width(15.dp))
                                Text(
                                    text = data.visuals.message,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                )
            },
            topBar = {
                Row(
                    modifier = Modifier
                        .wrapContentHeight()
                        .padding(start = 10.dp, end = 10.dp)
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
                                .size(25.dp),
                            imageVector = Icons.Default.ArrowBackIos, // Help icon
                            contentDescription = "Back",
                            tint = Color(0XFF00BCD4)
                        )
                    }

                    Text(
                        modifier = Modifier.weight(0.9f),
                        text = if (meters.isNotEmpty()) meters[0].title!! else "",
                        textAlign = TextAlign.Center,
                        color = Color(0XFF00BCD4),
                        style = MaterialTheme.typography.headlineMedium
                    )
                    IconButton(
                        modifier = Modifier.weight(0.1f),
                        onClick = {
                            navController.navigate(Screen.HISTORY.route + "/" + meters[0].meterId)
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
                PortraitLayout(meters, navController, viewModel, snackbarHostState, coroutineScope)
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
    viewModel: MeterViewModel,
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        userScrollEnabled = true
    )
    {
        items(meters.size) { i ->
            CalculationComponent(
                navController,
                viewModel,
                i,
                meters[i],
                snackbarHostState,
                coroutineScope
            )
        }
    }
}