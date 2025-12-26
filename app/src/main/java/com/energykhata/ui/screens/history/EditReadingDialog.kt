package com.energykhata.ui.screens.history

import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.energykhata.R
import com.energykhata.roomdb.models.Reading
import com.energykhata.util.scaledFontSize
import com.energykhata.util.scaledIconSize
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditReadingDialog(
    reading: Reading,
    onDismiss: () -> Unit,
    onConfirm: (Reading) -> Unit,
) {
    var editedReadingValue by remember { mutableStateOf(reading.reading.toString()) }
    var showDatePicker by remember { mutableStateOf(false) }

    // Initialize date picker with current reading's date
    val initialDate = Calendar.getInstance().apply {
        set(Calendar.DAY_OF_MONTH, reading.date.toIntOrNull() ?: 1)
        set(Calendar.MONTH, reading.month)
        set(Calendar.YEAR, reading.year)
    }.timeInMillis

    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = initialDate)
    val confirmEnabled by remember {
        derivedStateOf { datePickerState.selectedDateMillis != null }
    }

    var selectedDateMillis by remember { mutableLongStateOf(initialDate) }

    AlertDialog(
        onDismissRequest = onDismiss,
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
                                onClick = onDismiss
                            ),
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
                        text = "Edit Reading",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        fontSize = scaledFontSize(24f, 20f, 18f),
                        color = Color(0XFF008D9F)
                    )
                }
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = editedReadingValue,
                    onValueChange = {
                        // Allow only numeric input
                        if (it.all { char -> char.isDigit() }) {
                            editedReadingValue = it
                        }
                    },
                    label = { Text("Reading Value") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0XFFE6F8FB), RoundedCornerShape(10.dp))
                        .border(1.dp, Color(0XFF008D9F), RoundedCornerShape(10.dp))
                        .clickable { showDatePicker = true }
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
                    val dateText = dateFormat.format(Date(selectedDateMillis))
                    Text(
                        text = "Date: $dateText",
                        color = Color(0XFF008D9F),
                        fontSize = scaledFontSize(16f, 15f, 14f),
                        fontWeight = FontWeight.Bold
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.calender),
                        contentDescription = "Select Date",
                        tint = Color(0XFF008D9F),
                        modifier = Modifier.size(scaledIconSize(25f, (25f * 0.85f), (25f * 0.75f)))
                    )
                }
            }
        },
        confirmButton = {
            Button(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.outlinedButtonColors(Color(0XFF008D9F)),
                onClick = {
                    val newReadingValue = editedReadingValue.toIntOrNull() ?: reading.reading

                    val calendar = Calendar.getInstance().apply {
                        timeInMillis = selectedDateMillis
                    }
                    val newDate = calendar.get(Calendar.DAY_OF_MONTH).toString()
                    val newMonth = calendar.get(Calendar.MONTH)
                    val newYear = calendar.get(Calendar.YEAR)

                    val updatedReading = reading.copy(
                        reading = newReadingValue.toLong(),
                        date = newDate,
                        month = newMonth,
                        year = newYear
                    )
                    onConfirm(updatedReading)
                }
            ) {
                Text(
                    text = "Update",
                    color = Color.White,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    fontSize = scaledFontSize(15f, 14f, 13f),
                )
            }
        },
        dismissButton = {}
    )

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                Button(
                    onClick = {
                        datePickerState.selectedDateMillis?.let {
                            selectedDateMillis = it
                        }
                        showDatePicker = false
                    },
                    enabled = confirmEnabled
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                Button(onClick = { showDatePicker = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}