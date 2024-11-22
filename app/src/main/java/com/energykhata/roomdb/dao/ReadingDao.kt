package com.energykhata.roomdb.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.energykhata.roomdb.models.Reading

@Dao
interface ReadingDao {
    @Insert
    suspend fun insertReading(reading: Reading): Long

    @Delete
    suspend fun deleteReading(reading: Reading): Int

    @Query("SELECT * FROM readings WHERE meter_Id = :meterId")
    suspend fun getReadingByMeterId(meterId: Int): List<Reading>
}
