package com.energykhata.roomdb.repositories

import com.energykhata.roomdb.EnergyKhataDatabase
import com.energykhata.roomdb.models.Meter

class MeterRepository (private  val  db : EnergyKhataDatabase){

    suspend fun upsertMeter(meter: Meter):Long {
       return db.meterDao().upsertMeter(meter)
    }


    suspend fun deleteMeter(meter: Meter) {
         db.meterDao().deleteMeter(meter)
    }


    suspend fun getMeters(): List<Meter> {
        return db.meterDao().getMeters()
    }

    suspend fun getMeter(id : Long): List<Meter> {
        return db.meterDao().getMeter(id)
    }
}