package com.energykhata.roomdb.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.energykhata.roomdb.models.Meter

@Dao
interface MeterDao {
    @Upsert
    suspend fun upsertMeter(meter: Meter): Long

    @Delete
    suspend fun deleteMeter(meter: Meter): Int

    @Query("SELECT * FROM meters")
    suspend fun getMeters(): List<Meter>

    @Query("SELECT * FROM meters WHERE meter_id = :id")
    suspend fun getMeter(id : Int): List<Meter>
}
