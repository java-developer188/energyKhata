package com.energykhata.ui.screens.configuration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.energykhata.roomdb.models.Meter
import com.energykhata.ui.theme.ReadingRecorderTheme

@Composable
fun CMeter(index : Int ,meter : Meter) {
    // State variables to hold user input
    var title by remember { mutableStateOf(meter.title ?: "") }
    var isCategoryProtected by remember { mutableStateOf(meter.protectedCategory) }


    // UI Layout
    Column(
        modifier = Modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Divider()
        Text(
            fontSize = 7.sp,
            text = "Meter $index")
        Divider()


        // Title Input Field
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            placeholder = { Text("Ground Floor,First Floor etc") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp, 16.dp, 16.dp, 16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Text(text = "Category")
            Row(Modifier.selectableGroup()) {

                RadioButton(
                    selected = isCategoryProtected,
                    onClick = { isCategoryProtected = true },
                    modifier = Modifier.semantics { contentDescription = "Protected" }
                )
                Text(text = "Protected",
                    modifier = Modifier
                        .padding(5.dp,12.dp,16.dp,5.dp))
                RadioButton(
                    selected = !isCategoryProtected,
                    onClick = { isCategoryProtected = false },
                    modifier = Modifier.semantics { contentDescription = "Non-Protected" }
                )
                Text(text = "Non-Protected",
                    modifier = Modifier
                        .padding(5.dp,12.dp,16.dp,5.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReadingRecorderAppPreview() {
    ReadingRecorderTheme {
        CMeter(1,Meter(1,1,null,123,3.4f,false));
    }
}