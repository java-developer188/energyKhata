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

    suspend fun updateReading(reading: Reading) {
        db.readingDao().updateReading(reading)
    }

    suspend fun getReadingByMeterId(meterId: Long,month:Int , year:Int) : List<Reading> {
        return db.readingDao().getReadingByMeterId(meterId,month,year)
    }
}