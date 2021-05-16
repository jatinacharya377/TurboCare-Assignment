package com.example.turbocareassignment.repository.roomdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vehicle")
data class VehicleEntity(
    @ColumnInfo(name = "fuel_type")
    val fuelType: String,
    @ColumnInfo(name = "manufacturer")
    val manufacturer: String,
    @ColumnInfo(name = "model")
    val model: String,
    @ColumnInfo(name = "transmission")
    val transmission: String,
    @ColumnInfo(name = "vehicle_number")
    val vehicleNumber: String,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L
)
