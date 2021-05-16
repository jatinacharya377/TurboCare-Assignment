package com.example.turbocareassignment.repository.roomdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface VehicleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addVehicleEntity(vehicleEntity: VehicleEntity): Completable
    @Query("SELECT * FROM vehicle")
    fun getAllVehicles(): Single<List<VehicleEntity>>
    @Query("SELECT * FROM vehicle WHERE id = :id")
    fun getVehicleProfile(id: Long): Single<VehicleEntity>
}