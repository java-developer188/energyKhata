package com.energykhata.roomdb.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.energykhata.roomdb.models.Reading

@Dao
interface ReadingDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertReading(reading: Reading): Long

    @Delete
    suspend fun deleteReading(reading: Reading): Int

    @Update
    suspend fun updateReading(reading: Reading)

    @Query("SELECT * FROM readings WHERE meter_Id = :meterId and month=:month and year=:year order by reading_id desc ")
    suspend fun getReadingByMeterId(meterId: Long , month : Int, year: Int ): List<Reading>
}
