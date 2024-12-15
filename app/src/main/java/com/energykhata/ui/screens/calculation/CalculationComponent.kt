package com.energykhata.ui.screens.calculation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.energykhata.roomdb.models.Meter
import com.energykhata.roomdb.models.Reading
import com.energykhata.ui.theme.ReadingRecorderTheme
import com.energykhata.viewmodels.MeterViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.Calendar

@Composable
fun CalculationComponent(
    navController: NavHostController,
    viewModel: MeterViewModel,
    meterNumber: Int,
    meter: Meter,
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope
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
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Spacer(modifier = Modifier.height(25.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .focusable(),
            textStyle = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
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
                } catch (e: NumberFormatException) {

                }
            },
            enabled = isEditing,
            label = { Text("Previous Month Reading") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            trailingIcon = {
                if (isEditing) {

                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = "Save",
                        tint = Color(0XFF28A745),
                        modifier = Modifier
                            .clickable {
                                keyboardController?.hide()
                                if (currentReading != 0L && previousReading > currentReading) {
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar(
                                            message = "Oops! The previous reading cannot be greater than the current reading",
                                            duration = SnackbarDuration.Long
                                        )
                                    }
                                } else {
                                    isEditing = false
                                    //save this reading in database
                                    meter.previousReading = previousReading
                                    viewModel.updatePreviousMonthReading(meter)
                                }
                            }
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit",
                        tint = Color(0XFF00BCD4),
                        modifier = Modifier
                            .clickable {
                                focusRequester.requestFocus()
                                isEditing = true
                            }
                    )
                }
            },
            colors = TextFieldDefaults.colors(
                cursorColor = Color(0XFF00BCD4),
                focusedIndicatorColor = Color(0XFFFFC107),
                focusedTextColor = Color(0XFF00BCD4),
                focusedLabelColor = Color(0XFF00BCD4),
                focusedContainerColor = Color.Transparent,

                disabledContainerColor = Color(0XFFE9E9E9),
                disabledTextColor = Color(0XFFB3B2B2),
                disabledLabelColor = Color(0XFFB3B2B2),
                disabledPlaceholderColor = Color(0XFFB3B2B2),

                unfocusedTextColor = Color(0XFFB3B2B2),
                unfocusedLabelColor = Color(0XFFB3B2B2),
                unfocusedIndicatorColor = Color(0XFFBBBABA),
                unfocusedContainerColor = Color.Transparent,
            )
        )

        Spacer(modifier = Modifier.height(25.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
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
                } catch (e: NumberFormatException) {

                }

            },
            label = { Text("Current Reading") },
            singleLine = true,

            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            colors = TextFieldDefaults.colors(
                cursorColor = Color(0XFF00BCD4),
                focusedIndicatorColor = Color(0XFFFFC107),
                focusedTextColor = Color(0XFF00BCD4),
                focusedLabelColor = Color(0XFF00BCD4),
                focusedContainerColor = Color.Transparent,

                disabledContainerColor = Color(0XFFE9E9E9),
                disabledTextColor = Color(0XFFB3B2B2),
                disabledLabelColor = Color(0XFFB3B2B2),
                disabledPlaceholderColor = Color(0XFFB3B2B2),

                unfocusedTextColor = Color(0XFFB3B2B2),
                unfocusedLabelColor = Color(0XFFB3B2B2),
                unfocusedIndicatorColor = Color(0XFFBBBABA),
                unfocusedContainerColor = Color.Transparent,
            )
        )

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
                    .background(Color(0XFF00BCD4))
                    .clickable {
                        keyboardController?.hide()
                        // Update the difference when saving the previous reading
                        if (currentReading < previousReading) {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Oops! The current reading must be greater than the previous reading",
                                    duration = SnackbarDuration.Long
                                )
                            }
                        } else {
                            if (isEditing) {
                                isEditing = false
                                //When data is correct then save the
                                meter.previousReading = previousReading
                                viewModel.updatePreviousMonthReading(meter)
                            }
                            if (isSaveEnabled) {
                                var instant = Calendar.getInstance()
                                var date = instant.time.toString().split("GMT")[0].trim().substringBeforeLast(' ')
                                var time = instant.time.toString().split("GMT")[0].trim().substringAfterLast(' ')
                                viewModel.saveReadingInLogs(
                                    Reading(
                                        0,
                                        meter.meterId,
                                        currentReading,
                                        date,
                                        time,
                                        instant.get(Calendar.MONTH),
                                        instant.get(Calendar.YEAR)
                                    )
                                )
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
                            fontSize = TextUnit(45f, TextUnitType.Sp)
                        )
                    ) {
                        append("$unitsConsume")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = Color(0XFF28A745),
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


@Preview(showBackground = true)
@Composable
fun ReadingScreenPreview() {
    ReadingRecorderTheme {

    }
}