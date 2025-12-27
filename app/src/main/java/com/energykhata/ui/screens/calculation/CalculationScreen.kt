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
import com.energykhata.BuildConfig
import com.energykhata.R
import com.energykhata.factory.MeterViewModelFactory
import com.energykhata.roomdb.models.Meter
import com.energykhata.roomdb.repositories.MeterRepository
import com.energykhata.roomdb.repositories.ReadingRepository
import com.energykhata.ui.LockScreenOrientation
import com.energykhata.ui.Screen
import com.energykhata.ui.theme.EnergyTeal
import com.energykhata.util.BannerAd
import com.energykhata.util.scaledFontSize
import com.energykhata.util.scaledIconSize
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
                            meters.firstOrNull()?.meterId?.let { id ->
                                navController.navigate(Screen.HISTORY.route + "/" + id)
                            }
                        }
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(scaledIconSize(35f, (35f * 0.85f), (35f * 0.75f)))
                                .indication(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ),
                            painter = painterResource(id = R.drawable.history),
                            contentDescription = "History",
                            tint = EnergyTeal
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
                BannerAd(adUnitId = BuildConfig.AD_BANNER_ID)
            }
        }
    }
}
