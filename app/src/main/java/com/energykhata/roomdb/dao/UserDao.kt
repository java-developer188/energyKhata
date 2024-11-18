package com.energykhata.roomdb.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.energykhata.roomdb.models.User

@Dao
interface UserDao {
    @Upsert
    suspend fun insertUser(user: User): Long

    @Delete
    suspend fun deleteUser(user: User): Int

    @Query("SELECT * FROM users ")
    suspend fun getUsers(): List<User>
}