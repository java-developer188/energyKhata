package com.energykhata.roomdb.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "readings",
        foreignKeys = [
        ForeignKey(
            entity = Meter::class,
            parentColumns = arrayOf("meter_id"),
            childColumns = arrayOf("meter_id"),
            onDelete = ForeignKey.CASCADE
        )])
data class Reading(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "reading_id")
    val readingId: Int = 0,

    @ColumnInfo(name = "meter_id")
    val meterId: Int,

    @ColumnInfo(name = "reading")
    val reading: Long,

    @ColumnInfo(name = "date")
    var date : String
)

