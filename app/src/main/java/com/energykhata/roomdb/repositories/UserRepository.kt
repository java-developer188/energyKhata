package com.energykhata.roomdb.repositories

import com.energykhata.roomdb.EnergyKhataDatabase
import com.energykhata.roomdb.models.User

class UserRepository(private val db : EnergyKhataDatabase) {

    suspend fun insertUser(user: User) {
        db.userDao().insertUser(user)
    }


    suspend fun deleteUser(user: User) {
        db.userDao().deleteUser(user)
    }


    suspend fun getUsers() :List<User> {
        return db.userDao().getUsers()
    }
}