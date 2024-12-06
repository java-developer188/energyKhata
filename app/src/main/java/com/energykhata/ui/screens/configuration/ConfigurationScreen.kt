package com.energykhata.ui.screens.configuration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.energykhata.factory.ConfigScreenViewModelFactory
import com.energykhata.roomdb.repositories.MeterRepository
import com.energykhata.roomdb.repositories.UserRepository
import com.energykhata.ui.theme.ReadingRecorderTheme
import com.energykhata.viewmodels.ConfigScreenViewModel

@Composable
fun ConfigurationScreen(userRepository: UserRepository, meterRepository: MeterRepository) {
    val viewModel: ConfigScreenViewModel = viewModel(
        factory = ConfigScreenViewModelFactory(userRepository, meterRepository)
    )
    // State variables to hold user input

    val users by viewModel.users.collectAsState()
    val meters by viewModel.meters.collectAsState()
    var numberOfMeters by remember { mutableIntStateOf(0) }

    // UI Layout
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        if (users.isNotEmpty()) {
            CUser(user = users[0])
        }

        Spacer(modifier = Modifier.height(16.dp))

        ElevatedButton(onClick = {
            viewModel.addMeter();
        }) {
            Text("Add meter")
        }

        LazyColumn(
            modifier = Modifier
                .padding(10.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            userScrollEnabled = true
        ) {
            items(meters.size) { i ->
                CMeter(i,meters[i])
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            ElevatedButton(
                onClick = {
                    viewModel.saveData();
                }) {
                Text("Submit")
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getData()
    }
}

@Preview(showBackground = true)
@Composable
fun ConfigurationScreenPreview() {
    ReadingRecorderTheme {

    }
}