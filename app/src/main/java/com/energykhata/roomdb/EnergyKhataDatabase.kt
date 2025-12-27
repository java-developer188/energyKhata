package com.energykhata.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.energykhata.roomdb.dao.MeterDao
import com.energykhata.roomdb.dao.ReadingDao
import com.energykhata.roomdb.dao.UserDao
import com.energykhata.roomdb.models.Meter
import com.energykhata.roomdb.models.Reading
import com.energykhata.roomdb.models.User
import com.energykhata.util.Converter

@Database(
    entities = [User::class, Meter::class, Reading::class],
    version = 9,
    exportSchema = true
)
@TypeConverters(Converter::class)
abstract class EnergyKhataDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun meterDao(): MeterDao
    abstract fun readingDao(): ReadingDao

    companion object {
        // Add migrations here for future schema changes
        // Example:
        // val MIGRATION_9_10 = object : Migration(9, 10) {
        //     override fun migrate(database: SupportSQLiteDatabase) {
        //         database.execSQL("ALTER TABLE meters ADD COLUMN new_column TEXT")
        //     }
        // }

        fun getAllMigrations(): Array<Migration> {
            return arrayOf(
                // Add migrations here as they are created
                // MIGRATION_9_10
            )
        }
    }
}
