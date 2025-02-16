package com.energykhata.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.energykhata.R
import com.energykhata.util.scaledFontSize
import com.energykhata.util.scaledIconSize

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeterActionBottomSheet(
    isBottomSheetVisible: MutableState<Boolean>,// State to control bottom sheet visibility
    meterTitle: String,
    onDismiss: () -> Unit,
    onEdit: () -> Unit,                         // Callback for Edit action
    onHistory: () -> Unit,                      // Callback for History action
    onDelete: () -> Unit,                        // Callback for Delete action
) {
    if (isBottomSheetVisible.value) {
        ModalBottomSheet(
            onDismissRequest = {
                isBottomSheetVisible.value = false
                onDismiss()
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 25.dp),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = meterTitle,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineMedium,
                    fontSize = scaledFontSize(28f, 25f , 22f),
                    fontWeight = FontWeight.Bold,
                    color = Color(0XFF008D9F),
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, bottom = 10.dp)
                        .fillMaxWidth()
                )
                Divider(
                    thickness = 1.dp,
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .fillMaxWidth()
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent)
                        .padding(start = 16.dp, end = 16.dp, bottom = 100.dp),

                ) {
                    Row(
                        modifier = Modifier
                            .wrapContentHeight()
                            //.width(130.dp)
                            .weight(1f)
                            .clip(RoundedCornerShape(25))
                            .background(Color(0XFFE6F8FB))
                            .border(2.dp, Color(0XFF008D9F), RoundedCornerShape(25))
                            .clickable {
                                isBottomSheetVisible.value = false
                                onEdit()
                            },
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            style = MaterialTheme.typography.titleLarge,
                            fontSize = scaledFontSize(22f,20f,18f),
                            text = "Edit",
                            color = Color(0XFF008D9F),
                            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp, start = 10.dp)
                        )
                        Icon(
                            painter = painterResource(R.drawable.edit),
                            contentDescription = "Edit Icon",
                            tint = Color(0XFF008D9F),
                            modifier = Modifier
                                .size(scaledIconSize(35f, 33f , 31f))
                                .padding(start = 5.dp, end = 10.dp),
                        )
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    Row(
                        modifier = Modifier
                            .wrapContentHeight()
                            //.width(130.dp)
                            .weight(1f)
                            .clip(RoundedCornerShape(25))
                            .background(Color(0XFFE6F8FB))
                            .border(2.dp, Color(0XFF008D9F), RoundedCornerShape(25))
                            .clickable {
                                isBottomSheetVisible.value = false
                                onHistory()
                            },
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "History",
                            color = Color(0XFF008D9F),
                            style = MaterialTheme.typography.titleLarge,
                            fontSize = scaledFontSize(22f,20f,18f),
                            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp, start = 10.dp)
                        )
                        Icon(
                            painter = painterResource(R.drawable.history),
                            contentDescription = "History Icon",
                            tint = Color(0XFF008D9F),
                            modifier = Modifier
                                .size(scaledIconSize(35f, 33f , 31f))
                                .padding(start = 5.dp, end = 10.dp),
                        )
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    Row(
                        modifier = Modifier
                            .wrapContentHeight()
                            //.width(130.dp)
                            .weight(1f)
                            .clip(RoundedCornerShape(25))
                            .background(Color(0XFFE6F8FB))
                            .border(2.dp, Color(0XFF008D9F), RoundedCornerShape(25))
                            .clickable {
                                isBottomSheetVisible.value = false
                                onDelete()
                            },
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Delete",
                            color = Color(0XFF008D9F),
                            style = MaterialTheme.typography.titleLarge,
                            fontSize = scaledFontSize(22f,20f,18f),
                            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp, start = 10.dp)
                        )
                        Icon(
                            painter = painterResource(R.drawable.del_red),
                            contentDescription = "Delete Icon",
                            tint = Color(0XFFDC3545),
                            modifier = Modifier
                                .size(scaledIconSize(35f, 33f , 31f))
                                .padding(start = 5.dp, end = 10.dp),
                        )
                    }
                }
            }
        }
    }
}
