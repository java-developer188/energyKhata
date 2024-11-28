package com.energykhata

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Help
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.energykhata.roomdb.EnergyKhataDatabase
import com.energykhata.ui.screens.RootNavHost
import com.energykhata.ui.theme.ReadingRecorderTheme


class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            EnergyKhataDatabase::class.java,
            name = "energykhata.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var showDialog by remember { mutableStateOf(false) }
            val navController = rememberNavController()
            ReadingRecorderTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = {
                            Row(
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .fillMaxWidth()
                                    .background(Color(0xFF2196F3)),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                if (navController.previousBackStackEntry != null) {
                                    IconButton(
                                        modifier = Modifier.weight(0.1f),
                                        onClick = { navController.navigateUp() }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.ArrowBackIos, // Help icon
                                            contentDescription = "Back",
                                            tint = Color.White
                                        )
                                    }
                                }
                                Text(
                                    modifier = Modifier.weight(0.9f),
                                    text = "Energy Khata",
                                    textAlign = TextAlign.Center,
                                    color = Color.White,
                                    style = MaterialTheme.typography.headlineSmall
                                )
                                IconButton(
                                    modifier = Modifier.weight(0.1f),
                                    onClick = { showDialog = true }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Help, // Help icon
                                        contentDescription = "Help",
                                        tint = Color.White
                                    )
                                }
                            }
                        }
                    ) { paddingValues ->
                        Box(
                            modifier = Modifier.padding(paddingValues)
                        ) {
                            RootNavHost(db)
                        }
                    }
                }
            }





            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("Help") },
                    text = { Text("This is the Energy Khata app. Track your energy meter readings efficiently.") },
                    confirmButton = {
                        ElevatedButton(onClick = { showDialog = false }) {
                            Text("Close")
                        }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReadingRecorderAppPreview() {
    ReadingRecorderTheme {

    }
}


