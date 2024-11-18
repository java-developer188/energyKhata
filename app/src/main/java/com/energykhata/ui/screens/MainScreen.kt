package com.energykhata.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.energykhata.R
import com.energykhata.customfactory.MeterViewModelFactory
import com.energykhata.roomdb.repositories.MeterRepository
import com.energykhata.ui.screens.components.ReadingRecorder
import com.energykhata.viewmodels.MeterViewModel

@Composable
fun MainScreen(meterRepository: MeterRepository) {
    val viewModel: MeterViewModel = viewModel(
        factory = MeterViewModelFactory(meterRepository)
    )

            val meters by viewModel.meters.collectAsState()

            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.bglite),
                    contentDescription = null,
                    contentScale = ContentScale.None,
                    modifier = Modifier.fillMaxSize()
                )
                LazyRow(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    userScrollEnabled = true)
                {
                    items(meters.size) { i ->
                        Spacer(modifier = Modifier.width(25.dp))
                        Divider(
                            modifier = Modifier
                                .width(2.dp)
                                .fillMaxHeight(),
                            thickness = DividerDefaults.Thickness,
                            color = Color.Transparent)
                        Spacer(modifier = Modifier.width(25.dp))
                        ReadingRecorder(i,meters[i])
                        Spacer(modifier = Modifier.width(25.dp))
                        Divider(
                            modifier = Modifier
                                .width(2.dp)
                                .fillMaxHeight(),
                            thickness = DividerDefaults.Thickness,
                            color = Color.Transparent)
                        Spacer(modifier = Modifier.width(25.dp))
                    }
                }
            }
    LaunchedEffect(Unit) {
        viewModel.getMeter()
    }
}