package com.energykhata

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import com.energykhata.roomdb.EnergyKhataDatabase
import com.energykhata.ui.RootNavHost
import com.energykhata.ui.theme.ReadingRecorderTheme
import com.google.android.gms.ads.MobileAds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


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
        val backgroundScope = CoroutineScope(Dispatchers.IO)
        backgroundScope.launch {
            // Initialize the Google Mobile Ads SDK on a background thread.
            MobileAds.initialize(this@MainActivity) {}
        }
        setContent {
            var showDialog by remember { mutableStateOf(false) }
            ReadingRecorderTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RootNavHost(db)
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


