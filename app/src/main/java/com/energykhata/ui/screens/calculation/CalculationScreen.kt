package com.energykhata.ui.screens.calculation

import android.content.pm.ActivityInfo
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
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
import com.energykhata.factory.MeterViewModelFactory
import com.energykhata.roomdb.models.Meter
import com.energykhata.roomdb.repositories.MeterRepository
import com.energykhata.roomdb.repositories.ReadingRepository
import com.energykhata.ui.LockScreenOrientation
import com.energykhata.ui.Screen
import com.energykhata.util.BannerAd
import com.energykhata.viewmodels.MeterViewModel

@Composable
fun CalculationScreen(
    navController: NavHostController,
    meterId: Long?,
    meterRepository: MeterRepository,
    readingRepository: ReadingRepository,
) {
    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    val viewModel: MeterViewModel = viewModel(
        factory = MeterViewModelFactory(meterRepository, readingRepository)
    )

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
                        modifier = Modifier.weight(0.1f)
                            .indication(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ),
                        onClick = { navController.navigateUp() }
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(35.dp)
                                .indication(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ),
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
                            navController.navigate(Screen.HISTORY.route + "/" + meters[0].meterId)
                        }
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(35.dp),
//                            imageVector = Icons.Default.History,
                            painter = painterResource(id = R.drawable.history), // Help icon
                            contentDescription = "History",
                            tint = Color(0XFF008D9F)
                        )
                    }
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier.padding(paddingValues)
            ) {
                PortraitLayout(meters, viewModel)
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
    viewModel: MeterViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .weight(.6f),
            horizontalAlignment = Alignment.CenterHorizontally,
            userScrollEnabled = true
        )
        {
            items(meters.size) { i ->
                CalculationComponent(viewModel, i, meters[i])
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            Modifier
                .fillMaxWidth()
                .height(70.dp)
                .weight(.1f),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                // This commented Ad Unit ID is google testing code
                // BannerAd(adUnitId = "ca-app-pub-3940256099942544/9214589741")
//                BannerAd(adUnitId = "ca-app-pub-7592034253054302/2550847616")
                BannerAd(adUnitId = "ca-app-pub-8119818222880593/1535065254")
            }
        }
    }
}