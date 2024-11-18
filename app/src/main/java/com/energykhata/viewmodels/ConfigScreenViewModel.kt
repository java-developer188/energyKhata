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

class ConfigScreenViewModel (private val userRepository: UserRepository , private val meterRepository: MeterRepository ): ViewModel() {


    // MutableStateFlow to hold the list of users, initially an empty list
    private val _users = MutableStateFlow<List<User>>(emptyList())
    // Exposed as a read-only StateFlow
    val users: StateFlow<List<User>> = _users


    // MutableStateFlow to hold the list of users, initially an empty list
    private val _meters = MutableStateFlow<List<Meter>>(emptyList())
    // Exposed as a read-only StateFlow
    val meters: StateFlow<List<Meter>> = _meters


    fun getData() {
        viewModelScope.launch {
            val userRes = userRepository.getUsers()
            _users.value = userRes
        if (_users.value.isEmpty()) {
            _users.value += User()
        }
            val meterRes = meterRepository.getMeters()
            _meters.value = meterRes
        }
    }

    fun addMeter() {
       _meters.value+=Meter()
    }

    fun deleteMeter() {
        _meters.value.dropLast(1)
    }

    fun saveData() {
        viewModelScope.launch {
            val userRes = userRepository.getUsers()

            _users.value = userRes
            for (meter in _meters.value) {
                meterRepository.upsertMeter(meter)
            }
        }
    }
}