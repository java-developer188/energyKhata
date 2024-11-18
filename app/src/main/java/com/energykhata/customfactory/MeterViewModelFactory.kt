package com.energykhata.customfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.energykhata.roomdb.repositories.MeterRepository
import com.energykhata.viewmodels.MeterViewModel

class MeterViewModelFactory(private val meterRepository: MeterRepository) : ViewModelProvider.Factory {
     override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MeterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MeterViewModel(meterRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
