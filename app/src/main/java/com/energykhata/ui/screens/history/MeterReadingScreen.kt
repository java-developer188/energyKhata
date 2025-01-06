package com.energykhata.ui.screens.history

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.energykhata.R
import com.energykhata.roomdb.models.Meter
import com.energykhata.roomdb.models.Reading
import com.energykhata.util.BannerAd
import com.energykhata.viewmodels.ReadingViewModel
import java.util.Calendar

@Composable
fun MeterReadingScreen(
    viewModel: ReadingViewModel,
    meter: Meter,
    readings: List<Reading>,
) {

    val currentMonth = Calendar.getInstance().get(Calendar.MONTH)
    val year = Calendar.getInstance().get(Calendar.YEAR)

    var selectedMonth by remember { mutableIntStateOf(currentMonth) }
    var selectedYear by remember { mutableIntStateOf(year) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var readingToDelete by remember { mutableStateOf<Reading?>(null) }
    var showMonthYearPicker by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp)),
        ) {
            MonthPicker(
                visible = showMonthYearPicker,
                currentMonth = currentMonth,
                currentYear = year,
                confirmButtonCLicked = { month_, year_ ->
                    selectedMonth = month_
                    selectedYear = year_
                    viewModel.getReadings(meter.meterId!!, selectedMonth, selectedYear)
                    showMonthYearPicker = false
                },
                cancelClicked = {
                    showMonthYearPicker = false
                }
            )
            MonthYearRow(
                month = selectedMonth,
                year = selectedYear,
                icon = Icons.Default.CalendarMonth,
                onClick = {
                    showMonthYearPicker = true
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // LazyColumn for displaying readings
        LazyColumn(modifier = Modifier.weight(1f)) {
            if (readings.isNotEmpty()) {
                items(readings.size) { index ->
                    MeterReadingCard(
                        reading = readings[index],
                        onDeleteClick = {
                            readingToDelete = readings[index]
                            showDeleteDialog = true
                        }
                    )
                }
            } else {
                item {
                    Text(
                        text = "No records found.",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color(0XFFB3B2B2),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            Modifier
                .fillMaxWidth()
                .height(if (readings.size < 5) 120.dp else if (readings.size < 10) 100.dp else 80.dp),
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

    // Delete confirmation dialog
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Absolute.Right,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(25.dp)
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() },
                                    onClick = {
                                        showDeleteDialog = false
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
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color(0XFF008D9F)
                        )
                    }
                }
            },
            text = {
                Text(
                    "Are you sure you want to delete this reading?",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            },
            confirmButton = {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.outlinedButtonColors(Color(0XFF008D9F)),
                    onClick = {
                        readingToDelete?.let {
                            viewModel.deleteReading(
                                it,
                                meter.meterId!!,
                                selectedMonth,
                                selectedYear
                            )
                        }
                        showDeleteDialog = false
                    }) {
                    Text(
                        text = "Delete",
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            },
            dismissButton = {}
        )
    }
}

@Composable
fun MeterReadingCard(reading: Reading, onDeleteClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0XFFFDFDFD))
            .border(0.2.dp, Color(0XFFB3B2B2), RoundedCornerShape(10.dp))
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(.8f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier
                            .size(25.dp),
                        painter = painterResource(id = R.drawable.reading),
                        contentDescription = "Meter Reading",
                        tint = Color(0XFFB3B2B2)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${reading.reading}",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0XFF6B6B6B)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                // Date and Time
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(25.dp),
                            painter = painterResource(id = R.drawable.calender),
                            contentDescription = "Date",
                            tint = Color(0XFFB3B2B2)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = reading.date + ", " + reading.year,
                            color = Color(0XFFB3B2B2)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(25.dp),
                            painter = painterResource(id = R.drawable.time),
                            contentDescription = "Time",
                            tint = Color(0XFFB3B2B2)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = reading.time,
                            color = Color(0XFFB3B2B2)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Delete Icon
            Icon(
                painter = painterResource(R.drawable.del_red),
                contentDescription = "Delete Reading",
                tint = Color(0XFFDC3545),
                modifier = Modifier
                    .size(35.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = { onDeleteClick() })
                    .weight(.1f)
            )
        }
    }
}

@Composable
fun MonthYearRow(
    month: Int,
    year: Int,
    icon: ImageVector,
    onClick: () -> Unit, // Callback for the click action
) {
    val monthName = getMonthName(month)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick) // Make the row clickable
            .background(Color(0XFFE6F8FB))
            .clip(RoundedCornerShape(10.dp))
            .border(1.dp, Color(0XFF008D9F), RoundedCornerShape(10.dp)),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                //.weight(0.9f)
                .padding(top = 10.dp, bottom = 10.dp),
            text = "$monthName $year",
            style = MaterialTheme.typography.headlineMedium,
            color = Color(0XFF008D9F),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            modifier = Modifier
                //.weight(0.1f)
                .size(25.dp),
            imageVector = icon,
            contentDescription = "Month Icon",
            tint = Color(0XFF008D9F)
        )
    }
}

fun getMonthName(month: Int): String {
    return when (month) {
        0 -> "January"
        1 -> "February"
        2 -> "March"
        3 -> "April"
        4 -> "May"
        5 -> "June"
        6 -> "July"
        7 -> "August"
        8 -> "September"
        9 -> "October"
        10 -> "November"
        11 -> "December"
        else -> "Invalid month"
    }
}


@Preview(showBackground = true)
@Composable
fun MeterReadingScreenPreview() {
//    val sampleReadings = listOf(
//        Reading(1, 1, 211232, "14:45","3",11,2024),
//        Reading(2, 1, 3232, "09:30","2233",10,2023),
//        Reading(3, 1, 43434, "18:00","223",212,2022)
//    )
//    MeterReadingScreen(
//        meter= Meter(1,1,"Ground Floor",12,2.2f,false),
//        readings = sampleReadings,
//        onDeleteReading = { /* Handle Delete */ }
//    )
}
