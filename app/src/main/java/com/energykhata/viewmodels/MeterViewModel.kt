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

class MeterViewModel(
    private val meterRepository: MeterRepository,
    private val readingRepository: ReadingRepository
) : ViewModel() {

    private val _meters = MutableStateFlow<List<Meter>>(emptyList())
    val meters: StateFlow<List<Meter>> = _meters

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun getMeter(id: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val res = meterRepository.getMeter(id)
                _meters.value = res
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to load meter"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updatePreviousMonthReading(meter: Meter) {
        viewModelScope.launch {
            _error.value = null
            try {
                meterRepository.upsertMeter(meter)
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to update reading"
            }
        }
    }

    fun saveReadingInLogs(reading: Reading) {
        viewModelScope.launch {
            _error.value = null
            try {
                readingRepository.insertReading(reading)
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to save reading"
            }
        }
    }

    fun clearError() {
        _error.value = null
    }
}
