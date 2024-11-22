package com.energykhata.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.energykhata.roomdb.repositories.MeterRepository
import com.energykhata.roomdb.repositories.ReadingRepository
import com.energykhata.viewmodels.ReadingViewModel

class ReadingViewModelFactory(private val meterRepository: MeterRepository, private val readingRepository: ReadingRepository) : ViewModelProvider.Factory {
     override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReadingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ReadingViewModel(meterRepository, readingRepository ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
