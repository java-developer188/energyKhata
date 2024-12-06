package com.energykhata.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.energykhata.roomdb.models.Meter
import com.energykhata.roomdb.models.Reading
import com.energykhata.roomdb.repositories.MeterRepository
import com.energykhata.roomdb.repositories.ReadingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ReadingViewModel(private val meterRepository: MeterRepository, private val readingRepository: ReadingRepository) : ViewModel() {

    // MutableStateFlow to hold the list of meters, initially an empty list
    private val _readings = MutableStateFlow<List<Reading>>(emptyList())

    // Exposed as a read-only StateFlow
    val readings: StateFlow<List<Reading>> = _readings


    // MutableStateFlow to hold the list of meters, initially an empty list
    private val _meters = MutableStateFlow<List<Meter>>(emptyList())

    // Exposed as a read-only StateFlow
    val meters: StateFlow<List<Meter>> = _meters

    fun getReadings(id : Int) {
        viewModelScope.launch {
            val res = readingRepository.getReadingByMeterId(id)
            _readings.value = res
        }
    }

    fun getMeter(id : Int) {
        viewModelScope.launch {
            val res = meterRepository.getMeter(id)
            _meters.value = res
        }
    }

}
