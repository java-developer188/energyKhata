package com.energykhata.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.energykhata.roomdb.repositories.MeterRepository
import com.energykhata.roomdb.repositories.UserRepository
import com.energykhata.viewmodels.MainViewModel

class MainViewModelFactory(private val meterRepository: MeterRepository, private val userRepository: UserRepository) : ViewModelProvider.Factory {
     override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(meterRepository, userRepository ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
