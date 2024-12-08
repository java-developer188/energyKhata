package com.energykhata.ui.screens.main

import android.content.pm.ActivityInfo
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Help
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.energykhata.factory.MainViewModelFactory
import com.energykhata.roomdb.models.Meter
import com.energykhata.roomdb.repositories.MeterRepository
import com.energykhata.roomdb.repositories.UserRepository
import com.energykhata.ui.LockScreenOrientation
import com.energykhata.ui.Screen
import com.energykhata.viewmodels.MainViewModel


@Composable
fun MainScreen(
    navController: NavHostController,
    meterRepository: MeterRepository,
    userRepository: UserRepository
) {
    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    BackHandler {
        if (navController.previousBackStackEntry != null) {
            navController.popBackStack() // Navigate back in the stack
        }
    }
    val viewModel: MainViewModel = viewModel(
        factory = MainViewModelFactory(meterRepository, userRepository)
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
                        .padding(start = 10.dp, end = 10.dp)
                        .fillMaxWidth()
                        .background(Color.Transparent),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Absolute.Right
                ) {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(.9f)
                    )
                    IconButton(
                        modifier = Modifier.weight(0.1f),
                        onClick = { navController.navigate(Screen.HELP.route) }
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(25.dp),
                            imageVector = Icons.Default.Help, // Help icon
                            contentDescription = "Help",
                            tint = Color(0xFF00BCD4)
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
        viewModel.getMeters()
    }
}

@Composable
private fun PortraitLayout(
    meters: List<Meter>,
    navController: NavHostController,
    viewModel: MainViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(
            modifier = Modifier
                .weight(0.1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Energy Khata",
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0XFF00BCD4),
                style = MaterialTheme.typography.headlineLarge, // Big heading
                textAlign = TextAlign.Center // Center the text
            )
            Spacer(modifier = Modifier.height(8.dp)) // Add space between heading and text
            Text(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Normal,
                text = "Easily manage and store readings of your energy meters. Tap the Help button anytime for guidance on using the app.",
                style = MaterialTheme.typography.bodyLarge, // Regular text style
                textAlign = TextAlign.Center // Center the text
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
//        LazyColumn(
//            modifier = Modifier
//                .wrapContentSize()
//                .padding(10.dp)
//                .weight(0.5f),
//            verticalArrangement = Arrangement.SpaceBetween,
//            userScrollEnabled = true
//        )
//        {
//            items(meters.size) { i ->
//                MeterCard(
//                    meterName = meters[i].title!!,
//                    onClick = {navController.navigate(Screen.CALCULATION.route + "/" + (i + 1))},
//                    modifier = Modifier
//                )
//                Spacer(modifier = Modifier.height(10.dp))
//            }
//        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.5f)
        ) {
            MeterGridView(meters = meters, navController = navController)
        }
        if (meters.size < 8) {
            Row(modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .weight(0.1f)
                .clip(RoundedCornerShape(10.dp))
            ) {
                Row(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .background(Color(0XFF00BCD4))
                        .clickable { viewModel.addMeter() },
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        modifier = Modifier.padding(top = 19.dp),
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Meter",
                        tint = Color(0XFFFFF9E6)
                    )
                    Text(
                        modifier = Modifier.padding(top = 15.dp, bottom = 15.dp),
                        text = "Add Meter",
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center,
                        color = Color(0XFFFFF9E6)
                    )
                }
            }
        } else {
            Spacer(modifier = Modifier.height(10.dp))
        }

        Row(
            modifier = Modifier
                .weight(0.1f)
                .fillMaxSize()
                .background(Color.Cyan)
        ) {
            Text(text = "AD Banner")
        }
    }
}

@Composable
fun MeterGridView(meters: List<Meter>, navController: NavHostController) {

    var gridColors =
        listOf(Color(0XFFFFECB2), Color(0XFFE0E5FF), Color(0XFFE5F3B6), Color(0XFFD9F5F9))
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp),
        contentPadding = PaddingValues(1.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        items(meters.size) { index ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(gridColors[(index % gridColors.size)]),
                contentAlignment = Alignment.Center
            ) {
                MeterGridItem(
                    meterName = meters[index].title!!,
                    onClick = { navController.navigate(Screen.CALCULATION.route + "/" + (meters[index].meterId)) },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MeterGridScreenPreview() {
    val sampleMeters = listOf(
        Meter(1, 1, "Ground Floor ", 1234, 30.0f, true),
        Meter(1, 1, "First Floor ", 1234, 30.0f, true),
        Meter(1, 1, "First Floor ", 1234, 30.0f, true),
        Meter(1, 1, "First Floor ", 1234, 30.0f, true),
        Meter(1, 1, "First Floor ", 1234, 30.0f, true),

        )
    MeterGridView(meters = sampleMeters, navController = rememberNavController())
}