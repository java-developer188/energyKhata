package com.energykhata.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.energykhata.roomdb.models.Meter
import com.energykhata.roomdb.models.User
import com.energykhata.roomdb.repositories.MeterRepository
import com.energykhata.roomdb.repositories.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val meterRepository: MeterRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _meters = MutableStateFlow<List<Meter>>(emptyList())
    val meters: StateFlow<List<Meter>> = _meters

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun getMeters() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                var res = meterRepository.getMeters()
                if (res.isEmpty()) {
                    createDefaultUser()
                    meterRepository.upsertMeter(Meter(1, 1, "Meter 1", 0, 0.0f, false))
                    res = meterRepository.getMeters()
                }
                _meters.value = res
            } catch (e: Exception) {
                _error.value = e.message ?: "An error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addMeter() {
        viewModelScope.launch {
            _error.value = null
            try {
                val users = userRepository.getUsers()
                if (users.isNotEmpty()) {
                    var maxMeterId = meterRepository.getMeters().mapNotNull { it.meterId }.maxOrNull()
                    if (maxMeterId != null) {
                        maxMeterId += 1
                    } else {
                        maxMeterId = 1
                    }
                    meterRepository.upsertMeter(
                        Meter(maxMeterId, users[0].userId, "Meter $maxMeterId", 0, 0.0f, false)
                    )
                }
                _meters.value = meterRepository.getMeters()
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to add meter"
            }
        }
    }

    fun updateMeter(meter: Meter) {
        viewModelScope.launch {
            _error.value = null
            try {
                meterRepository.upsertMeter(meter)
                _meters.value = meterRepository.getMeters()
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to update meter"
            }
        }
    }

    fun deleteMeter(meter: Meter) {
        viewModelScope.launch {
            _error.value = null
            try {
                meterRepository.deleteMeter(meter)
                _meters.value = meterRepository.getMeters()
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to delete meter"
            }
        }
    }

    fun clearError() {
        _error.value = null
    }

    private suspend fun createDefaultUser() {
        val userList = userRepository.getUsers()
        if (userList.isEmpty()) {
            userRepository.insertUser(User(1, "Default", ""))
        }
    }
}
