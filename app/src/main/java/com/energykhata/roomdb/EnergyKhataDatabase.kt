package com.energykhata.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.energykhata.roomdb.dao.MeterDao
import com.energykhata.roomdb.dao.ReadingDao
import com.energykhata.roomdb.dao.UserDao
import com.energykhata.roomdb.models.Meter
import com.energykhata.roomdb.models.Reading
import com.energykhata.roomdb.models.User
import com.energykhata.util.Converter

@Database(entities = [  User::class,
                        Meter::class,
                        Reading::class],
    version =6 )
@TypeConverters(Converter::class)
abstract class EnergyKhataDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun meterDao(): MeterDao
    abstract fun readingDao(): ReadingDao
}
