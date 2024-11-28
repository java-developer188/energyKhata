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

class MainViewModel(private val meterRepository: MeterRepository, private val userRepository: UserRepository) : ViewModel() {

    // MutableStateFlow to hold the list of meters, initially an empty list
    private val _meters = MutableStateFlow<List<Meter>>(emptyList())

    // Exposed as a read-only StateFlow
    val meters: StateFlow<List<Meter>> = _meters

    fun getMeters() {
        viewModelScope.launch {
            var res = meterRepository.getMeters()
            if (res.isEmpty()) {
                createDefaultUser()
                meterRepository.upsertMeter(Meter( 1, 1, "Meter 1", 0, 0.0f, false))
                res = meterRepository.getMeters()
            }
            _meters.value = res
        }
    }

     fun addMeter() {
        viewModelScope.launch {
            val res = meterRepository.getMeters()
            if (res.isNotEmpty()) {
                val num = res.size + 1
                meterRepository.upsertMeter(Meter(num, 1, "Meter $num", 0, 0.0f, false))
            }
            _meters.value = meterRepository.getMeters()
        }
    }

    private suspend fun createDefaultUser() {
        userRepository.insertUser(User(1, "Default", ""))
    }
}
