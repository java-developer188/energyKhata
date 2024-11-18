package com.energykhata.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.energykhata.roomdb.models.Meter
import com.energykhata.roomdb.repositories.MeterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MeterViewModel (private val repository : MeterRepository): ViewModel() {

    // MutableStateFlow to hold the list of meters, initially an empty list
    private val _meters = MutableStateFlow<List<Meter>>(emptyList())

    // Exposed as a read-only StateFlow
    val meters: StateFlow<List<Meter>> = _meters
    fun getMeter() {
        viewModelScope.launch {
                val res = repository.getMeters()
            _meters.value = res
//            userMeters = repository.getMeterByUserId(1)
//                .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
        }
    }
}
