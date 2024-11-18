package com.energykhata

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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

//    private val meterViewModel by viewModels<MeterViewModel>(
//        factoryProducer = {
//            object : ViewModelProvider.Factory {
//                override fun <T : ViewModel> create(modelClass: Class<T>): T {
//                    return MeterViewModel(MeterRepository(db)) as T
//                }
//            }
//        }
//    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReadingRecorderTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RootNavHost(db)
                }
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


