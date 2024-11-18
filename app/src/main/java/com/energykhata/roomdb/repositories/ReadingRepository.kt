package com.energykhata.roomdb.repositories

import com.energykhata.roomdb.EnergyKhataDatabase
import com.energykhata.roomdb.models.Reading

class ReadingRepository(private val db : EnergyKhataDatabase) {

    suspend fun insertReading(reading: Reading) {
        db.readingDao().insertReading(reading)
    }


    suspend fun deleteReading(reading: Reading) {
        db.readingDao().deleteReading(reading)
    }


    fun getReadingByMeterId(meterId: Int){
        db.readingDao().getReadingByMeterId(meterId)
    }
}