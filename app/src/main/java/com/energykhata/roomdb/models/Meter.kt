package com.energykhata.roomdb.models
import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "meters",
        foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = arrayOf("user_id"),
            childColumns = arrayOf("user_id"),
            onDelete = ForeignKey.CASCADE
        )])
data class Meter(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "meter_id")
    var meterId: Int = 0,

    @ColumnInfo(name = "user_id")
    var userId: Int = 0,

    @ColumnInfo(name = "title")
    @Nullable
    var title: String? = "",

    @ColumnInfo(name = "previous_reading")
    var previousReading : Long = 0,

    @ColumnInfo(name = "rate")
    var rate : Float = 0.0f,

    @ColumnInfo(name = "protected_category")
    var protectedCategory: Boolean = true
)

