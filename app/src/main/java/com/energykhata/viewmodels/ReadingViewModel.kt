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

class ReadingViewModel(
    private val meterRepository: MeterRepository,
    private val readingRepository: ReadingRepository
) : ViewModel() {

    private val _readings = MutableStateFlow<List<Reading>>(emptyList())
    val readings: StateFlow<List<Reading>> = _readings

    private val _meters = MutableStateFlow<List<Meter>>(emptyList())
    val meters: StateFlow<List<Meter>> = _meters

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun getReadings(meterId: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val res = readingRepository.getReadingByMeterId(
                    meterId,
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.YEAR)
                )
                _readings.value = res
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to load readings"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getReadings(meterId: Long, month: Int, year: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val res = readingRepository.getReadingByMeterId(meterId, month, year)
                _readings.value = res
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to load readings"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getMeter(meterId: Long) {
        viewModelScope.launch {
            _error.value = null
            try {
                val res = meterRepository.getMeter(meterId)
                _meters.value = res
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to load meter"
            }
        }
    }

    fun deleteReading(reading: Reading, meterId: Long, month: Int, year: Int) {
        viewModelScope.launch {
            _error.value = null
            try {
                readingRepository.deleteReading(reading)
                val res = readingRepository.getReadingByMeterId(meterId, month, year)
                _readings.value = res
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to delete reading"
            }
        }
    }

    fun clearError() {
        _error.value = null
    }
}
