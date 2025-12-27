package com.energykhata

import android.app.Application
import androidx.room.Room
import com.energykhata.roomdb.EnergyKhataDatabase
import com.energykhata.roomdb.repositories.MeterRepository
import com.energykhata.roomdb.repositories.ReadingRepository
import com.energykhata.roomdb.repositories.UserRepository

class EnergyKhataApp : Application() {

    val database: EnergyKhataDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            EnergyKhataDatabase::class.java,
            "energykhata.db"
        )
            .addMigrations(*EnergyKhataDatabase.getAllMigrations())
            .build()
    }

    val meterRepository: MeterRepository by lazy {
        MeterRepository(database)
    }

    val userRepository: UserRepository by lazy {
        UserRepository(database)
    }

    val readingRepository: ReadingRepository by lazy {
        ReadingRepository(database)
    }
}
