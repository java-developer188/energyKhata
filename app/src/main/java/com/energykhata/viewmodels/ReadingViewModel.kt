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
import java.util.Calendar

class ReadingViewModel(private val meterRepository: MeterRepository, private val readingRepository: ReadingRepository) : ViewModel() {

    // MutableStateFlow to hold the list of meters, initially an empty list
    private val _readings = MutableStateFlow<List<Reading>>(emptyList())

    // Exposed as a read-only StateFlow
    val readings: StateFlow<List<Reading>> = _readings


    // MutableStateFlow to hold the list of meters, initially an empty list
    private val _meters = MutableStateFlow<List<Meter>>(emptyList())

    // Exposed as a read-only StateFlow
    val meters: StateFlow<List<Meter>> = _meters

    fun getReadings(meterId : Int) {
        viewModelScope.launch {
            val res = readingRepository.getReadingByMeterId(meterId,Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.YEAR))
            _readings.value = res
        }
    }
    fun getReadings(meterId : Int , month : Int , year : Int) {
        viewModelScope.launch {
            val res = readingRepository.getReadingByMeterId(meterId,month,year)
            _readings.value = res
        }
    }

    fun getMeter(meterId : Int) {
        viewModelScope.launch {
            val res = meterRepository.getMeter(meterId)
            _meters.value = res
        }
    }

    fun deleteReading(reading : Reading,meterId : Int , month : Int , year : Int) {
        viewModelScope.launch {
            readingRepository.deleteReading(reading)
            val res = readingRepository.getReadingByMeterId(meterId,month,year)
            _readings.value = res
        }
    }

}
