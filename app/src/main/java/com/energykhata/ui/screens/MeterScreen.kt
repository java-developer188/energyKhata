package com.energykhata.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.energykhata.R
import com.energykhata.factory.MeterViewModelFactory
import com.energykhata.roomdb.repositories.MeterRepository
import com.energykhata.roomdb.repositories.ReadingRepository
import com.energykhata.ui.screens.components.ReadingRecorder
import com.energykhata.viewmodels.MeterViewModel

@Composable
fun MeterScreen(
    navController: NavHostController,
    meterId: Int?,
    meterRepository: MeterRepository,
    readingRepository: ReadingRepository
) {
    val viewModel: MeterViewModel = viewModel(
        factory = MeterViewModelFactory(meterRepository,readingRepository)
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(25.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            userScrollEnabled = true)
        {
            items(meters.size) { i ->
                ReadingRecorder(navController,viewModel,i,meters[i])
            }
        }
    }
    LaunchedEffect(Unit) {
        if (meterId != null) {
            viewModel.getMeter(meterId)
        }
    }
}