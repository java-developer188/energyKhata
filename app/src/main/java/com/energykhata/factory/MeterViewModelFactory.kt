package com.energykhata.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.energykhata.roomdb.repositories.MeterRepository
import com.energykhata.roomdb.repositories.ReadingRepository
import com.energykhata.viewmodels.MeterViewModel

class MeterViewModelFactory(private val meterRepository: MeterRepository ,private val readingRepository: ReadingRepository) : ViewModelProvider.Factory {
     override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MeterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MeterViewModel(meterRepository, readingRepository ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
