package com.energykhata.roomdb.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "readings",
        foreignKeys = [
        ForeignKey(
            entity = Meter::class,
            parentColumns = arrayOf("meter_id"),
            childColumns = arrayOf("meter_id"),
            onDelete = ForeignKey.CASCADE
        )],
    indices = [Index(value = ["reading"], unique = true)])
data class Reading(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "reading_id")
    var readingId: Long?,

    @ColumnInfo(name = "meter_id")
    var meterId: Long?,

    @ColumnInfo(name = "reading")
    var reading: Long,

    @ColumnInfo(name = "date")
    var date : String,

    @ColumnInfo(name = "time")
    var time : String,

    @ColumnInfo(name = "month")
    var month : Int,

    @ColumnInfo(name = "year")
    var year : Int
)

