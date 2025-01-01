package com.energykhata.ui.screens.help

import android.content.pm.ActivityInfo
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.energykhata.R
import com.energykhata.ui.LockScreenOrientation
import com.energykhata.ui.Screen
import com.energykhata.util.BannerAd

@Composable
fun HelpScreen(
    navController: NavHostController,
) {
    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
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
                        modifier = Modifier.weight(0.1f),
                        onClick = { navController.navigateUp() }
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(35.dp),
                            painter = painterResource(id = R.drawable.arrow_back), // Help icon
                            contentDescription = "Back",
                            tint = Color(0XFF00BCD4)
                        )
                    }

                    Text(
                        modifier = Modifier.weight(0.9f),
                        text = "Help",
                        textAlign = TextAlign.Center,
                        color = Color(0XFF00BCD4),
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.ExtraBold
                    )
                    IconButton(
                        modifier = Modifier.weight(0.1f),
                        onClick = {
                            navController.navigate(Screen.MAIN.route)
                            {
                                popUpTo(Screen.MAIN.route) {
                                    inclusive = true
                                } // Clear the back stack
                            }
                        },

                        ) {
                        Icon(
                            modifier = Modifier
                                .size(35.dp),
//                            imageVector = Icons.Default.Home, // Help icon
                            painter = painterResource(id = R.drawable.home),
                            contentDescription = "Home",
                            tint = Color(0XFF00BCD4)
                        )
                    }
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier.padding(paddingValues)
            ) {
                PortraitLayout()
            }
        }
    }
}


@Composable
fun PortraitLayout() {

    val helpItems = listOf(
        "Add a Meter" to "Tap the Add Meter button to add a new meter._The app assigns a random name, which you can rename anytime._You can add up to 8 meters.",
        "Edit a Meter" to "Long press a meter tile._Tap \"Edit\" from the displayed menu._Edit the meter name in the dialog box (up to 16 characters) and save the changes.",
        "Delete a Meter" to "Long press a meter tile._Select \"Delete\" from the displayed menu._Confirm the action in the dialog box to permanently delete the meter.",
        "Calculate Units Consumed" to "Tap the meter tile to open the Calculation Screen._For first-time use: Enter the Previous Reading and save it._Enter your Current Reading, ensuring it’s greater than the previous one._Press Calculate to see the units consumed._The current reading is automatically saved to the history.",
        "Edit Previous Reading" to "Open the Calculation Screen by tapping your meter tile._Tap the Edit button next to the Previous Reading field._Enter the new value (must be smaller than the current reading).",
        "Skip Saving Current Reading" to "By default, every calculated current reading is saved in the history._To prevent saving, toggle off the Save Current Reading switch before calculation.",
        "View Meter History" to "Option 1: Long press the meter tile and select History from the menu._Option 2: Open the Calculation Screen for the desired meter and tap the History icon in the top-right corner._By default, the current month's history is displayed in descending order._To view the history of a specific month, tap the Calendar button at the top and select the desired month._Each record shows the meter reading along with the date and time it was calculated.",
        "Delete a History Record" to "Open the History Screen for desired meter._Locate the record you want to delete._Tap the Delete button at the end of the record's row._Confirm the action in the dialog box to permanently delete the record."


    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(2.dp),
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.9f)
        ) {
            items(helpItems.size) { index ->
                ExpandableHelpItem(
                    title = helpItems[index].first, content = helpItems[index].second
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            Modifier
                .fillMaxWidth()
                .height(70.dp)
                .weight(0.1f),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
            ) {
//              This commented Ad Unit ID is google testing code
//              BannerAd(adUnitId = "ca-app-pub-3940256099942544/9214589741")
//              BannerAd(adUnitId = "ca-app-pub-7592034253054302/2550847616")
                BannerAd(adUnitId = "ca-app-pub-8119818222880593/1535065254")
            }
        }
    }
}

@Composable
fun ExpandableHelpItem(title: String, content: String) {
    var isExpanded by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                isExpanded = !isExpanded
            }
            .background(
                if (!isExpanded) Color.Transparent
                else Color(0XFFFDFDFD),
                shape = RoundedCornerShape(10)
            )
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = Color(0XFF00BCD4),
                modifier = Modifier
                    .weight(1f)
            )
            Icon(
                imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                contentDescription = if (isExpanded) "Collapse" else "Expand",
                tint = Color(0XFF00BCD4)
            )
        }
        AnimatedVisibility(visible = isExpanded) {
            BulletText(text = content, delimiter = "_")
        }
    }
}

@Composable
fun BulletText(text: String, delimiter: String) {
    var bulletPoints = text.split(delimiter)
    Column {
        for (point in bulletPoints) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            ) {
                Text(
                    text = "•",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = point,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

