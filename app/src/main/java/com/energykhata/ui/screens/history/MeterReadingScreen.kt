package com.energykhata.ui.screens.history

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Timeline
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                    viewModel.getReadings(meter.meterId, selectedMonth, selectedYear)
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
        LazyColumn (modifier = Modifier.weight(1f)){
            items(readings.size) { index ->
                MeterReadingCard(
                    reading = readings[index],
                    onDeleteClick = {
                        readingToDelete = readings[index]
                        showDeleteDialog = true
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            Modifier
                .fillMaxWidth()
                .height(if (readings.size < 5) 250.dp else if (readings.size < 10) 150.dp else 100.dp)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
            ) {
               BannerAd(adUnitId = "ca-app-pub-3940256099942544/9214589741")
            }
        }
    }

    // Delete confirmation dialog
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
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
                        modifier = Modifier.clickable { showDeleteDialog = false },
                        imageVector = Icons.Default.Cancel,
                        contentDescription = "Cancel",
                        tint = Color(0XFF00BCD4)

                    )
                }
            },
            text = { Text("Are you sure you want to delete this reading?") },
            confirmButton = {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.outlinedButtonColors(Color(0XFF00BCD4)),
                    onClick = {
                        readingToDelete?.let {
                            viewModel.deleteReading(it, meter.meterId, selectedMonth, selectedYear)
                        }
                        showDeleteDialog = false
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

@Composable
fun MeterReadingCard(reading: Reading, onDeleteClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.Transparent)
            .border(0.2.dp, Color(0XFFB3B2B2), RoundedCornerShape(10.dp)),
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(.8f)
            ) {
                Row {
                    Icon(
                        imageVector = Icons.Default.Timeline,
                        contentDescription = "Meter Reading",
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${reading.reading}",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                // Date and Time
                Row {
                    Row {
                        Icon(Icons.Default.Event, contentDescription = "Date")
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = reading.date + ", " + reading.year)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Row {
                        Icon(Icons.Default.Schedule, contentDescription = "Time")
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = reading.time)
                    }
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Delete Icon
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete Reading",
                tint = Color.Red,
                modifier = Modifier
                    .size(32.dp)
                    .clickable { onDeleteClick() }
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
            .border(1.dp, Color(0XFF00BCD4), RoundedCornerShape(10.dp)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(0.9f)
                .padding(top = 20.dp, bottom = 20.dp),
            fontFamily = FontFamily.SansSerif,
            text = "$monthName $year",
            style = MaterialTheme.typography.headlineMedium,
            color = Color(0XFF00BCD4),
            fontSize = 25.sp,
            textAlign = TextAlign.Center
        )
        Icon(
            modifier = Modifier
                .weight(0.1f)
                .size(25.dp),
            imageVector = icon,
            contentDescription = "Month Icon",
            tint = Color(0XFF00BCD4)
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
