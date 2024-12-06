package com.energykhata.ui.screens.history

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.energykhata.roomdb.models.Reading
import java.util.Calendar

@Composable
fun MeterReadingScreen(meterName: String, readings: List<Reading>, onDeleteReading: (Reading) -> Unit) {

    val currentMonth = Calendar.getInstance().get(Calendar.MONTH)
    val year = Calendar.getInstance().get(Calendar.YEAR)

    var selectedMonth by remember { mutableIntStateOf(currentMonth) }
    var selectedYear by remember { mutableIntStateOf(year) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var readingToDelete by remember { mutableStateOf<Reading?>(null) }
    var showMonthYearPicker by remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            MonthPicker(
                visible = showMonthYearPicker,
                currentMonth = currentMonth,
                currentYear = year,
                confirmButtonCLicked = { month_, year_ ->
                    selectedMonth = month_
                    selectedYear = year_
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
        LazyColumn {
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
    }

    // Delete confirmation dialog
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Confirm Deletion") },
            text = { Text("Are you sure you want to delete this reading?") },
            confirmButton = {
                Button(onClick = {
                    readingToDelete?.let { onDeleteReading(it) }
                    showDeleteDialog = false
                }) {
                    Text("Delete")
                }
            },
            dismissButton = {
                Button(onClick = { showDeleteDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun MeterReadingCard(reading: Reading, onDeleteClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top= 8.dp, bottom = 8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.elevatedCardElevation()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon and Reading Value
            Icon(
                imageVector = Icons.Default.Timeline,
                contentDescription = "Meter Reading",
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "${reading.reading} kWh",
                style = MaterialTheme.typography.headlineMedium,
                fontSize = 20.sp,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Date and Time
            Column {
                Row {
                    Icon(Icons.Default.Event, contentDescription = "Date")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = reading.date)
                }
                Row {
                    Icon(Icons.Default.Schedule, contentDescription = "Time")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = reading.date)
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
            )
        }
    }
}

@Composable
fun MonthYearRow(
    month: Int,
    year: Int,
    icon: ImageVector,
    onClick: () -> Unit // Callback for the click action
) {
    val monthName = getMonthName(month)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick) // Make the row clickable
            .background(Color(0XFFE6F8FB))
            .clip(RoundedCornerShape(5.dp)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(0.9f).padding(top = 20.dp, bottom = 20.dp),
            fontFamily = FontFamily.SansSerif,
            text = "$monthName $year",
            style = MaterialTheme.typography.headlineMedium,
            color = Color(0XFF00BCD4),
            fontSize = 25.sp,
            textAlign = TextAlign.Center
        )
        Icon(
            modifier = Modifier.weight(0.1f).size(25.dp),
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
    val sampleReadings = listOf(
        Reading(1, 1, 211232, "14:45"),
        Reading(2, 1, 3232, "09:30"),
        Reading(3, 1, 43434, "18:00")
    )
    MeterReadingScreen(
        meterName = "Main House",
        readings = sampleReadings,
        onDeleteReading = { /* Handle Delete */ }
    )
}
