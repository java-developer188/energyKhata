package com.energykhata.ui.screens.main

import android.content.pm.ActivityInfo
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Help
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.energykhata.factory.MainViewModelFactory
import com.energykhata.roomdb.models.Meter
import com.energykhata.roomdb.repositories.MeterRepository
import com.energykhata.roomdb.repositories.UserRepository
import com.energykhata.ui.LockScreenOrientation
import com.energykhata.ui.Screen
import com.energykhata.util.BannerAd
import com.energykhata.viewmodels.MainViewModel
import kotlinx.coroutines.launch


@Composable
fun MainScreen(
    navController: NavHostController,
    meterRepository: MeterRepository,
    userRepository: UserRepository,
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
    var refreshState by remember { mutableStateOf(1) }

    fun deleteMeter(
        meterSelected: Meter?,
    ) {
        viewModel.deleteMeter(meterSelected!!)
        (meters as ArrayList).remove(meterSelected)
        refreshState++
    }

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
                        .padding(10.dp)
                        .fillMaxWidth()
                        .background(Color.Transparent),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Absolute.Right
                ) {
                    Text(
                        modifier = Modifier
                            .padding(10.dp)
                            .weight(.9f),
                        text = "Energy Khata",
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0XFF00BCD4),
                        style = MaterialTheme.typography.headlineLarge, // Big heading
                        textAlign = TextAlign.Left // Center the text
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
                if (refreshState > 0) {
                    PortraitLayout(
                        meters,
                        navController,
                        viewModel,
                        onDeleteMeter = { meter -> deleteMeter(meter) }
                    )
                }
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
    viewModel: MainViewModel,
    onDeleteMeter: (Meter?) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(
            modifier = Modifier
                .weight(0.1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
//            Text(
//                text = "Energy Khata",
//                fontFamily = FontFamily.SansSerif,
//                fontWeight = FontWeight.ExtraBold,
//                color = Color(0XFF00BCD4),
//                style = MaterialTheme.typography.headlineLarge, // Big heading
//                textAlign = TextAlign.Center // Center the text
//            )
            Spacer(modifier = Modifier.height(8.dp)) // Add space between heading and text
            Text(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Normal,
                text = "Easily manage and store readings of your energy meters. Tap the Help button anytime for guidance on using the app.",
                style = MaterialTheme.typography.bodyLarge, // Regular text style
                textAlign = TextAlign.Left // Center the text
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.5f)
        ) {
            MeterGridView(meters = meters, navController = navController, viewModel = viewModel,onDeleteMeter)
        }
        if (meters.size < 8) {
            Row(
                modifier = Modifier
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
                .background(Color.Transparent)
        ) {
            // This commented Ad Unit ID is google testing code
            // BannerAd(adUnitId = "ca-app-pub-3940256099942544/9214589741")
            BannerAd(adUnitId = "ca-app-pub-7592034253054302/2550847616")
        }
    }
}

@Composable
fun MeterGridView(meters: List<Meter>, navController: NavHostController, viewModel: MainViewModel,onDeleteMeter:(Meter?)->Unit) {

    var gridColors =
        listOf(Color(0XFFFFECB2), Color(0XFFE0E5FF), Color(0XFFE5F3B6), Color(0XFFD9F5F9))
    var meterSelected: Meter? by remember { mutableStateOf(null) }
    var showLongPressMenu by remember { mutableStateOf(false) }
    var editTitle by remember { mutableStateOf(false) }
    var newTitle by remember { mutableStateOf("") }
    var isDeleteMeter by remember { mutableStateOf(false) }
    val lazyGridState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()

    fun isItemVisible(lazyGridState: LazyGridState, index: Int): Boolean {
        val visibleItems = lazyGridState.layoutInfo.visibleItemsInfo
        return visibleItems.any { it.index == index }
    }

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
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(10.dp))
                        .background(gridColors[(index % gridColors.size)])
                        .border(2.dp,
                            if(meterSelected != null && (meterSelected!!.meterId == meters[index].meterId)) Color(0XFFFFC107)
                            else gridColors[(index % gridColors.size)] ,
                            RoundedCornerShape(10.dp))
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onLongPress = {
                                    showLongPressMenu = true
                                    meterSelected = meters[index]
                                    if (!isItemVisible(lazyGridState, index)) {
                                        coroutineScope.launch {
                                            lazyGridState.scrollToItem(index)
                                        }
                                    }
                                },
                                onTap = {
                                    navController.navigate(Screen.CALCULATION.route + "/" + (meters[index].meterId))
                                },
                            )
                        },
                    contentAlignment = Alignment.Center
                ) {
                    MeterGridItem(
                        meterName = meters[index].title!!
                    )
                }
                if (showLongPressMenu && meterSelected != null && meterSelected?.meterId == meters[index].meterId) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Row(
                        modifier = Modifier
                            .border(1.dp, Color(0XFF00BCD4), RoundedCornerShape(10.dp))
                            .clip(RoundedCornerShape(10.dp))
                            .wrapContentHeight()
                            .fillMaxWidth()
                            .background(Color(0XFFFDFDFD)),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier.clickable {
                                showLongPressMenu = false
                                meterSelected = meters[index]
                                editTitle = true
                                isDeleteMeter = false
                                newTitle = meters[index].title!!
                            },
                        ) {
                            Icon(
                                modifier = Modifier.padding(
                                    top = 10.dp,
                                    start = 10.dp,
                                    end = 5.dp,
                                    bottom = 10.dp
                                ),
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit",
                                tint = Color(0XFF00BCD4)
                            )
                            Text(
                                modifier = Modifier.padding(top = 7.dp),
                                text = "Edit",
                                style = MaterialTheme.typography.headlineSmall,
                                textAlign = TextAlign.Center,
                                color = Color(0XFF00BCD4)
                            )
                        }
                        Icon(
                            modifier = Modifier
                                .padding(10.dp)
                                .clickable {
                                    showLongPressMenu = false
                                    meterSelected = meters[index]
                                    editTitle = false
                                    isDeleteMeter = true
                                    newTitle = ""
                                },
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = Color(0XFFDC3545)
                        )
                    }
                }
            }
        }
    }
    if (editTitle) {
        AlertDialog(
            onDismissRequest = {
                editTitle = false
                meterSelected = null
            },
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Edit Meter Title",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0XFF00BCD4)
                    )
                    Icon(
                        modifier = Modifier.clickable {
                            editTitle = false
                            meterSelected = null
                        },
                        imageVector = Icons.Default.Cancel,
                        contentDescription = "Cancel",
                        tint = Color(0XFF00BCD4)

                    )
                }
            },
            text = {
                TextField(
                    textStyle = MaterialTheme.typography.bodyLarge,
                    singleLine = true,
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight(),

                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        cursorColor = Color(0XFF00BCD4),
                        focusedTextColor = Color(0XFF00BCD4),
                        unfocusedTextColor = Color(0XFF00BCD4),
                    ),
                    enabled = true,
                    value = newTitle,
                    onValueChange = {
                        newTitle = if (it.length <= 15) {
                            it
                        } else {
                            it.substring(0, 15)
                        }
                    }
                )
            },
            confirmButton = {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.outlinedButtonColors(Color(0XFF00BCD4)),
                    onClick = {
                        meterSelected?.title = newTitle
                        viewModel.updateMeter(meterSelected!!)
                        meterSelected = null
                        editTitle = false
                    }) {
                    Text(
                        text = "Save",
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            dismissButton = {}
        )
    }

    if (isDeleteMeter) {
        AlertDialog(
            onDismissRequest = { isDeleteMeter = false
                               meterSelected = null},
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Confirm Deletion",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0XFF00BCD4)
                    )
                    Icon(
                        modifier = Modifier.clickable {
                            isDeleteMeter = false
                            meterSelected = null
                                                      },
                        imageVector = Icons.Default.Cancel,
                        contentDescription = "Cancel",
                        tint = Color(0XFF00BCD4)

                    )
                }
            },
            text = { Text("Are you sure you want to delete \"${meterSelected?.title}\" meter?") },
            confirmButton = {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.outlinedButtonColors(Color(0XFF00BCD4)),
                    onClick = {
                        onDeleteMeter(meterSelected)
                        isDeleteMeter = false
                        meterSelected = null
                    }) {
                    Text(
                        text = "Delete",
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            dismissButton = {}
        )
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
    //MeterGridView(meters = sampleMeters, navController = rememberNavController())
}