package com.energykhata.ui.screens.main

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.energykhata.R
import com.energykhata.factory.MainViewModelFactory
import com.energykhata.roomdb.models.Meter
import com.energykhata.roomdb.repositories.MeterRepository
import com.energykhata.roomdb.repositories.UserRepository
import com.energykhata.ui.LockScreenOrientation
import com.energykhata.ui.Screen
import com.energykhata.util.BannerAd
import com.energykhata.util.scaledFontSize
import com.energykhata.util.scaledIconSize
import com.energykhata.viewmodels.MainViewModel


@Composable
fun MainScreen(
    navController: NavHostController,
    meterRepository: MeterRepository,
    userRepository: UserRepository,
) {
    val context = LocalContext.current
    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    BackHandler {
        (context as? Activity)?.finish() //Exit the application

        //just stop the further navigation
//        if (navController.previousBackStackEntry != null) {
//            navController.popBackStack() // Navigate back in the stack
//        }
    }
    val viewModel: MainViewModel = viewModel(
        factory = MainViewModelFactory(meterRepository, userRepository)
    )

    val meters by viewModel.meters.collectAsState()
    var refreshState by remember { mutableIntStateOf(1) }
    var addButtonEnable by remember { mutableStateOf(false) }

    fun deleteMeter(
        meterSelected: Meter?,
    ) {
        viewModel.deleteMeter(meterSelected!!)
        (meters as ArrayList).remove(meterSelected)
        refreshState++
    }

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
                    horizontalArrangement = Arrangement.Absolute.Right
                ) {
                    Row(
                        modifier = Modifier
                            .wrapContentSize()
                            .weight(.9f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 5.dp)
                                .weight(.9f),
                            text = "Energy Khata",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = scaledFontSize(45f, 35f, 25f),
                            color = Color(0XFF008D9F),
                            style = MaterialTheme.typography.headlineLarge, // Big heading
                            textAlign = TextAlign.Left // Center the text
                        )
                    }
                    IconButton(
                        onClick = { navController.navigate(Screen.HELP.route) },
                        modifier = Modifier
                            .weight(0.1f)
                            .indication(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            )
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(scaledIconSize(35f, (35f * 0.85f), (35f * 0.75f)))
                                .indication(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ),
//                            imageVector = Icons.Default.Help, // Help icon
                            painter = painterResource(id = R.drawable.help),
                            contentDescription = "Help",
                            tint = Color(0XFF008D9F)
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
                        addButtonEnable,
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
        addButtonEnable = true
    }
}

@Composable
private fun PortraitLayout(
    meters: List<Meter>,
    addButtonEnable: Boolean,
    navController: NavHostController,
    viewModel: MainViewModel,
    onDeleteMeter: (Meter?) -> Unit,
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .padding(bottom = 5.dp)
        ) {
            Text(
//                fontWeight = FontWeight.Normal,
                fontSize = scaledFontSize(16f, 14f, 12f),
                modifier = Modifier.scale(1.0f),
                text = "Easily manage and store readings of your energy meters. Tap the Help button anytime for guidance on using the app.",
                style = MaterialTheme.typography.labelLarge,
                textAlign = TextAlign.Left
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .weight(0.5f)

        ) {
            MeterGridView(
                meters = meters,
                navController = navController,
                viewModel = viewModel,
                onDeleteMeter
            )
        }

        if (addButtonEnable && (meters.size < 8)) {
            Spacer(modifier = Modifier.height(5.dp))
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
                        .background(Color(0XFF008D9F))
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
                        fontSize = scaledFontSize(24f, 22f, 20f),
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center,
                        color = Color(0XFFFFF9E6)
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
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
            //BannerAd(adUnitId = "ca-app-pub-7592034253054302/2550847616")
            BannerAd(adUnitId = "ca-app-pub-8119818222880593/1535065254")

        }
    }
}

@Composable
fun MeterGridView(
    meters: List<Meter>,
    navController: NavHostController,
    viewModel: MainViewModel,
    onDeleteMeter: (Meter?) -> Unit,
) {

    var borderRadiantColors =
        listOf(
            Brush.radialGradient(
                colors = listOf(Color(0xFF008798), Color(0xFF00BCD4)),
                radius = 600f
            ),
        )
    val iconColorListA =
        listOf(Color(0xFFE6F2FF), Color(0xFFD4E9FF), Color(0xFFDDFBFF), Color(0xFFCDF9FF))
    val iconColorListB =
        listOf(Color(0xFFD4E9FF), Color(0xFFDDFBFF), Color(0xFFCDF9FF), Color(0xFFE6F2FF))
    var meterSelected: Meter? by remember { mutableStateOf(null) }
    var showLongPressMenu by remember { mutableStateOf(false) }
    var isBottomSheetVisible = remember { mutableStateOf(false) }
    var editTitle by remember { mutableStateOf(false) }
    var newTitle by remember { mutableStateOf("") }
    var isDeleteMeter by remember { mutableStateOf(false) }
    var parentSize by remember { mutableStateOf(androidx.compose.ui.geometry.Size.Zero) }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp),
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
                        .background(if (index < 4) iconColorListA[(index % iconColorListA.size)] else iconColorListB[(index % iconColorListB.size)])
                        .border(1.dp, borderRadiantColors[0], RoundedCornerShape(10.dp))
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onLongPress = {
                                    showLongPressMenu = true
                                    isBottomSheetVisible.value = true
                                    meterSelected = meters[index]
                                    editTitle = false
                                    isDeleteMeter = false
                                    newTitle = ""
                                },
                                onTap = {
                                    navController.navigate(Screen.CALCULATION.route + "/" + (meters[index].meterId))
                                },
                            )
                        }
                        .onGloballyPositioned { coordinates ->
                            parentSize = coordinates.size.toSize()
                        },
                    contentAlignment = Alignment.Center
                ) {
//                    Image(
//                        painter = painterResource(id = R.drawable.meterboxpattern),
//                        contentDescription = null,
//                        contentScale = ContentScale.FillHeight,
//                        modifier = Modifier.fillMaxSize(),
//                    )
                    MeterGridItem(
                        meterName = meters[index].title!!
                    )
                }
            }
        }
    }
    // Bottom Sheet Composable
    MeterActionBottomSheet(
        isBottomSheetVisible = isBottomSheetVisible,
        meterTitle = if (meterSelected != null) meterSelected!!.title!! else "",
        onDismiss = {
            showLongPressMenu = false
            meterSelected = null
            editTitle = false
            isDeleteMeter = false
            newTitle = ""
        },
        onEdit = {
            showLongPressMenu = false
            editTitle = true
            isDeleteMeter = false
            newTitle = meterSelected!!.title!!
        },
        onHistory = {
            showLongPressMenu = false
            isDeleteMeter = false
            editTitle = false
            newTitle = ""
            navController.navigate(Screen.HISTORY.route + "/" + meterSelected!!.meterId)
        },
        onDelete = {
            showLongPressMenu = false
            isDeleteMeter = true
            editTitle = false
            newTitle = ""
        }
    )

    if (editTitle) {
        AlertDialog(
            onDismissRequest = {
                editTitle = false
                meterSelected = null
            },
            title = {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Absolute.Right,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(scaledIconSize(25f, (25f * 0.85f), (25f * 0.75f)))
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() },
                                    onClick = {
                                        editTitle = false
                                        meterSelected = null
                                    }),
                            painter = painterResource(id = R.drawable.close),
                            contentDescription = "Cancel",
                            tint = Color(0XFFDC3545)

                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Meter Title",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = scaledFontSize(24f, 22f, 20f),
                            color = Color(0XFF008D9F)
                        )

                    }
                }
            },
            text = {
                Row(modifier = Modifier.fillMaxWidth()) {
                    TextField(
                        textStyle = MaterialTheme.typography.titleLarge,
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),

                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            cursorColor = Color(0XFF008D9F),
                            focusedTextColor = Color(0XFF008D9F),
                            unfocusedTextColor = Color(0XFF008D9F),
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
                }
            },
            confirmButton = {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.outlinedButtonColors(Color(0XFF008D9F)),
                    onClick = {
                        meterSelected?.title = newTitle
                        viewModel.updateMeter(meterSelected!!)
                        meterSelected = null
                        editTitle = false
                    }) {
                    Text(
                        text = "Save",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.labelMedium,
                        fontSize = scaledFontSize(15f, 14f, 13f),
                    )
                }
            },
            dismissButton = {}
        )
    }

    if (isDeleteMeter) {
        AlertDialog(
            onDismissRequest = {
                isDeleteMeter = false
                meterSelected = null
            },
            title = {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Absolute.Right,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(scaledIconSize(25f, (25f * 0.85f), (25f * 0.75f)))
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() },
                                    onClick = {
                                        isDeleteMeter = false
                                        meterSelected = null
                                    }),
                            painter = painterResource(id = R.drawable.close),
                            contentDescription = "Cancel",
                            tint = Color(0XFFDC3545)

                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Confirm Deletion",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = scaledFontSize(24f, 20f, 18f),
                            color = Color(0XFF008D9F)
                        )
                    }
                }
            },
            text = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        "Are you sure you want to delete \"${meterSelected?.title}\" ?",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        fontSize = scaledFontSize(16f, 15f, 14f),
                    )
                }
            },
            confirmButton = {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.outlinedButtonColors(Color(0XFF008D9F)),
                    onClick = {
                        onDeleteMeter(meterSelected)
                        isDeleteMeter = false
                        meterSelected = null
                    }) {
                    Text(
                        text = "Delete",
                        color = Color.White,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        fontSize = scaledFontSize(15f, 14f, 13f),
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