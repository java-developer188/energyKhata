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



    suspend fun getReadingByMeterId(meterId: Int,month:Int , year:Int) : List<Reading> {
        return db.readingDao().getReadingByMeterId(meterId,month,year)
    }
}