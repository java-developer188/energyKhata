package com.energykhata.ui.screens.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.energykhata.roomdb.models.Meter
import com.energykhata.roomdb.models.Reading
import com.energykhata.ui.screens.Screen
import com.energykhata.viewmodels.MeterViewModel
import java.time.LocalDate

@Composable
fun ReadingRecorder(
    navController: NavHostController,
    viewModel: MeterViewModel,
    meterNumber: Int,
    meter: Meter
) {
    //<a href="https://www.vecteezy.com/free-vector/meter-reading">Meter Reading Vectors by Vecteezy</a>
    var title by remember { mutableStateOf(meter.title ?: "Meter $meterNumber") }
    var previousReading by remember { mutableLongStateOf(meter.previousReading) }
    var currentReading by remember { mutableLongStateOf(0) }
    var unitsConsume by remember { mutableLongStateOf(0) }
    var isEditing by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        )
        {
            Text(
                text = title,
                fontSize = TextUnit(25f, TextUnitType.Sp),
                fontWeight = FontWeight.Bold
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            OutlinedTextField(
                value = if (previousReading == 0L) "" else previousReading.toString(),
                onValueChange = {
                    try {
                        previousReading = if (it.isNotEmpty())
                            it.toLong()
                        else
                            0
                    } catch (e: NumberFormatException) {
                        Toast.makeText(
                            context,
                            "Only numbers allowed",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                enabled = isEditing,
                label = { Text("Previous Month Reading") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.width(10.dp))

            if (isEditing) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(50.dp)
                        .padding(5.dp)
                        .background(Color(0XFFeff4f9), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = "Save",
                        tint = Color(0XFF00668b),
                        modifier = Modifier
                            .clickable {
                                if (currentReading != 0L && previousReading > currentReading) {
                                    Toast
                                        .makeText(
                                            context,
                                            "Previous reading cannot be greater than current reading",
                                            Toast.LENGTH_SHORT
                                        )
                                        .show()
                                } else {
                                    isEditing = false
                                    //save this reading in database
                                    meter.previousReading = previousReading
                                    viewModel.updatePreviousMonthReading(meter)
                                }
                            }
                    )
                }
            } else {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(50.dp)
                        .padding(5.dp)
                        .background(Color(0XFFeff4f9), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit",
                        tint = Color(0XFF00668b),
                        modifier = Modifier
                            .clickable { isEditing = true }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = if (currentReading == 0L) "" else currentReading.toString(),
                onValueChange = {
                    try {
                        currentReading = if (it.isNotEmpty()) {
                            it.toLong()
                        } else {
                            0
                        }
                    } catch (e: NumberFormatException) {
                        Toast.makeText(
                            context,
                            "Only numbers allowed",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                },
                label = { Text("Current Reading") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.width(8.dp))

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(50.dp)
                    .padding(5.dp)
                    .background(Color(0XFFeff4f9), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = "Save reading",
                    tint = Color(0XFF00668b),
                    modifier = Modifier.clickable {
                        Toast.makeText(
                            context,
                            "Reading Saved",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.Center,
            modifier = Modifier.fillMaxWidth()
        )
        {
            ElevatedButton(
                modifier = Modifier.weight(0.8f),
                onClick = {
                    viewModel.saveReadingInLogs(Reading(0,meter.meterId,currentReading, LocalDate.now()))
                }
            ) {
                Text("Save Current Reading")
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.Center,
            modifier = Modifier.fillMaxWidth()
        )
        {
            ElevatedButton(
                modifier = Modifier.weight(0.8f),
                onClick = {
                    navController.navigate(Screen.READINGSCREEN.route+"/"+meter.meterId)
                }) {
                Text("Show History")
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.Center,
            modifier = Modifier.fillMaxWidth()
        )
        {
            ElevatedButton(
                modifier = Modifier.weight(0.9f),
                onClick = {

                // Update the difference when saving the previous reading
                if (currentReading < previousReading) {
                    Toast.makeText(
                        context,
                        "Current reading cannot be less than previous reading",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    if(isEditing) {
                        isEditing = false
                        //When data is correct then save the
                        meter.previousReading = previousReading
                        viewModel.updatePreviousMonthReading(meter)
                    }
                    unitsConsume = currentReading - previousReading
                }
            }) {
                Text("Calculate Units Consumed")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        )
        {
            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            color = Color(0XFF00668b),
                            fontSize = TextUnit(45f, TextUnitType.Sp)
                        )
                    ) {
                        append("$unitsConsume")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = Color(0XFF00668b),
                            fontSize = TextUnit(20f, TextUnitType.Sp)
                        )
                    ) {
                        append("  units")
                    }
                }
            )
        }
    }
}