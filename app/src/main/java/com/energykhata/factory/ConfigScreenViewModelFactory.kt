package com.energykhata.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.energykhata.roomdb.repositories.MeterRepository
import com.energykhata.roomdb.repositories.UserRepository
import com.energykhata.viewmodels.ConfigScreenViewModel

class ConfigScreenViewModelFactory(private val userRepository: UserRepository, private val meterRepository: MeterRepository) : ViewModelProvider.Factory {
     override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConfigScreenViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ConfigScreenViewModel(userRepository,meterRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
