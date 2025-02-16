package com.energykhata.ui.screens.calculation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.energykhata.R
import com.energykhata.roomdb.models.Meter
import com.energykhata.roomdb.models.Reading
import com.energykhata.ui.theme.ReadingRecorderTheme
import com.energykhata.util.scaledFontSize
import com.energykhata.util.scaledIconSize
import com.energykhata.viewmodels.MeterViewModel
import java.text.SimpleDateFormat
import java.util.Calendar

@Composable
fun CalculationComponent(
    viewModel: MeterViewModel,
    meterNumber: Int,
    meter: Meter,
) {
    //<a href="https://www.vecteezy.com/free-vector/meter-reading">Meter Reading Vectors by Vecteezy</a>
    var title by remember { mutableStateOf(meter.title ?: "Meter $meterNumber") }
    var previousReading by remember { mutableLongStateOf(meter.previousReading) }
    var currentReading by remember { mutableLongStateOf(0) }
    var unitsConsume by remember { mutableLongStateOf(0) }
    var isEditing by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    var isSaveEnabled by remember { mutableStateOf(true) }
    val keyboardController = LocalSoftwareKeyboardController.current
    var previousReadingError by remember { mutableStateOf(false) }
    var currentReadingError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Text(
            fontWeight = FontWeight.Normal,
            text = "Calculate units instantly and track monthly usage effortlessly!",
            style = MaterialTheme.typography.labelLarge,
            fontSize = scaledFontSize(16f,14f,12f),
            textAlign = TextAlign.Left
        )
        Spacer(modifier = Modifier.height(25.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .focusable(),
            textStyle = TextStyle(
                fontWeight = FontWeight.SemiBold,
                fontSize = scaledFontSize(24f,22f,20f),
                letterSpacing = 1.em
            ),
            value = if (previousReading == 0L) "" else previousReading.toString(),
            onValueChange = {
                try {
                    previousReading = if (it.isNotEmpty())
                        if (it.length <= 10) {
                            it.toLong()
                        } else {
                            it.substring(0, 10).toLong()
                        }
                    else
                        0
                } catch (_: NumberFormatException) {
                }

                if (previousReading == 0L) previousReadingError = false
                else if (previousReading < currentReading) {
                    previousReadingError = false
                    currentReadingError = false
                }

            },
            enabled = isEditing,
            label = {
                Text(
                    text = "Previous Month Reading",
                    style = MaterialTheme.typography.labelMedium,
                    fontSize = scaledFontSize(15f,13f,11f)
                )
            },
            singleLine = true,
            isError = previousReadingError,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            trailingIcon = {
                if (isEditing) {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = "Save",
                        tint = Color(0XFF28A745),
                        modifier = Modifier
                            .size(scaledIconSize(35f, (35f * 0.85f), (35f * 0.75f)))
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() },
                                onClick = {
                                    keyboardController?.hide()
                                    if (currentReading != 0L && previousReading > currentReading) {
                                        previousReadingError = true
                                    } else {
                                        isEditing = false
                                        previousReadingError = false
                                        currentReadingError = false
                                        //save this reading in database
                                        meter.previousReading = previousReading
                                        viewModel.updatePreviousMonthReading(meter)
                                    }
                                }
                            ))
                } else {
                    Icon(
                        painter = painterResource(R.drawable.edit),
                        contentDescription = "Edit",
                        tint = Color(0XFF008D9F),
                        modifier = Modifier
                            .size(scaledIconSize(35f, (35f * 0.85f), (35f * 0.75f)))
                            .clickable(indication = null,
                                interactionSource = remember { MutableInteractionSource() },
                                onClick = {
                                    isEditing = true
                                }
                            ))
                }
            },
            colors = TextFieldDefaults.colors(
                cursorColor = Color(0XFF008D9F),
                focusedIndicatorColor = Color(0XFFFFC107),
                focusedTextColor = Color(0XFF008D9F),
                focusedLabelColor = Color(0XFF008D9F),
                focusedContainerColor = Color.White,

                disabledContainerColor = Color(0XFFE9E9E9),
                disabledTextColor = Color(0XFFB3B2B2),
                disabledLabelColor = Color(0XFFB3B2B2),
                disabledPlaceholderColor = Color(0XFFB3B2B2),

                unfocusedTextColor = Color(0XFFB3B2B2),
                unfocusedLabelColor = Color(0XFFB3B2B2),
                unfocusedIndicatorColor = Color(0XFFBBBABA),
                unfocusedContainerColor = Color.White,

                errorPlaceholderColor = Color(0XFFDC3545),
                errorIndicatorColor = Color(0XFFDC3545),
            )
        )
        if (previousReadingError) {
            Text(
                modifier = Modifier
                    .background(Color.Transparent)
                    .padding(top = 10.dp),
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Normal,
                fontSize = scaledFontSize(12f,11f,10f),
                text = "The previous reading cannot be greater than the current reading",

                color = Color(0XFFDC3545)
            )
        }
        Spacer(modifier = Modifier.height(25.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(
                fontWeight = FontWeight.SemiBold,
                fontSize = scaledFontSize(24f,22f,20f),
                letterSpacing = 1.em
            ),
            value = if (currentReading == 0L) "" else currentReading.toString(),
            onValueChange = {
                try {
                    currentReading = if (it.isNotEmpty()) {
                        if (it.length <= 10) {
                            it.toLong()
                        } else {
                            it.substring(0, 10).toLong()
                        }
                    } else {
                        0
                    }
                } catch (_: NumberFormatException) {
                }

                if (currentReading == 0L) currentReadingError = false
                else if (currentReading > previousReading) {
                    previousReadingError = false
                    currentReadingError = false
                }

            },
            label = {
                Text(
                    text = "Current Reading",
                    style = MaterialTheme.typography.labelMedium,
                    fontSize = scaledFontSize(15f,13f,11f)
                )},
            singleLine = true,
            isError = currentReadingError,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            colors = TextFieldDefaults.colors(
                cursorColor = Color(0XFF008D9F),
                focusedIndicatorColor = Color(0XFFFFC107),
                focusedTextColor = Color(0XFF008D9F),
                focusedLabelColor = Color(0XFF008D9F),
                focusedContainerColor = Color.White,

                disabledContainerColor = Color(0XFFE9E9E9),
                disabledTextColor = Color(0XFFB3B2B2),
                disabledLabelColor = Color(0XFFB3B2B2),
                disabledPlaceholderColor = Color(0XFFB3B2B2),

                unfocusedTextColor = Color(0XFFB3B2B2),
                unfocusedLabelColor = Color(0XFFB3B2B2),
                unfocusedIndicatorColor = Color(0XFFBBBABA),
                unfocusedContainerColor = Color.White,

                errorPlaceholderColor = Color(0XFFDC3545),
                errorIndicatorColor = Color(0XFFDC3545),
            )
        )
        if (currentReadingError) {
            Text(
                modifier = Modifier
                    .background(Color.Transparent)
                    .padding(top = 10.dp),
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Normal,
                fontSize = scaledFontSize(12f,11f,10f),
                text = "The current reading must be greater than the previous reading",
                color = Color(0XFFDC3545)
            )
        }

        Spacer(modifier = Modifier.height(25.dp))

        SaveReadingToggle(
            isSaveEnabled = isSaveEnabled,
            onToggleChange = { isSaveEnabled = it }
        )

        Spacer(modifier = Modifier.height(25.dp))

        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
        ) {
            Row(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .background(
                        if (previousReading > 0 && currentReading > 0) Color(0XFF008D9F) else Color(
                            0XFFB3B2B2
                        )
                    )
                    .clickable(enabled = previousReading > 0 && currentReading > 0) {
                        keyboardController?.hide()
                        // Update the difference when saving the previous reading
                        if (currentReading < previousReading) {
                            currentReadingError = true
                        } else {
                            previousReadingError = false
                            currentReadingError = false
                            if (isEditing) {
                                isEditing = false
                                //When data is correct then save the
                                meter.previousReading = previousReading
                                viewModel.updatePreviousMonthReading(meter)
                            }
                            if (isSaveEnabled) {
                                if (currentReading > 0) {
                                    val instant = Calendar.getInstance()
                                    val date = instant.time
                                        .toString()
                                        .split("GMT")[0]
                                        .trim()
                                        .substringBeforeLast(' ')
                                    val time = SimpleDateFormat("hh:mm a", java.util.Locale.getDefault()).format(instant.time)

                                    viewModel.saveReadingInLogs(
                                        Reading(
                                            null,
                                            meter.meterId,
                                            currentReading,
                                            date,
                                            time,
                                            instant.get(Calendar.MONTH),
                                            instant.get(Calendar.YEAR)
                                        )
                                    )
                                }
                            }
                            unitsConsume = currentReading - previousReading
                        }

                    },
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.padding(top = 15.dp, bottom = 15.dp),
                    text = "Calculate Units",
                    style = MaterialTheme.typography.headlineSmall,
                    fontSize = scaledFontSize(24f,20f,18f),
                    textAlign = TextAlign.Center,
                    color = Color(0XFFFFF9E6)
                )
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

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
                            color = Color(0XFF28A745),
                            fontSize = scaledFontSize(45f,40f,35f)
                        )
                    ) {
                        append("$unitsConsume")
                    }
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            color = Color(0XFF28A745),
                            fontSize = TextUnit(2f, TextUnitType.Em)
                        )
                    ) {
                        append("  units")
                    }
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ReadingScreenPreview() {
    ReadingRecorderTheme {

    }
}