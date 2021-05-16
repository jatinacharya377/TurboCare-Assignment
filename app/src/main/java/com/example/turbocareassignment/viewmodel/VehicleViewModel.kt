package com.example.turbocareassignment.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.example.turbocareassignment.repository.roomdb.VehicleDatabase
import com.example.turbocareassignment.repository.roomdb.VehicleEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableCompletableObserver

import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class VehicleViewModel(application: Application): AndroidViewModel(application) {

    private val disposable = CompositeDisposable()
    private val isVehicleAdded = MutableLiveData<Boolean>()
    private val vehicleList = MutableLiveData<List<VehicleEntity>>()
    private val vehicleProfile = MutableLiveData<VehicleEntity>()
    private val db = Room.databaseBuilder(application, VehicleDatabase::class.java, "vehicle.db").build()
    private val vehicleDao = db.vehicleDao()

    fun getVehicleList(): LiveData<List<VehicleEntity>> {

        disposable.add(
            vehicleDao.getAllVehicles()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<VehicleEntity>>() {

                    override fun onSuccess(value: List<VehicleEntity>) {

                        vehicleList.postValue(value)
                    }

                    override fun onError(e: Throwable) {

                        e.message?.let { Log.e("Vehicle List error", it) }
                    }
                })
        )

        return vehicleList
    }

    fun getVehicleProfile(id: Long): LiveData<VehicleEntity> {

        disposable.add(
            vehicleDao.getVehicleProfile(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<VehicleEntity>() {

                    override fun onSuccess(value: VehicleEntity) {

                        vehicleProfile.postValue(value)
                    }

                    override fun onError(e: Throwable) {

                        e.message?.let { Log.e("Vehicle Profile error", it) }
                    }
                })
        )

        return vehicleProfile
    }

    fun insertVehicleEntity(vehicleEntity: VehicleEntity):LiveData<Boolean> {

        disposable.add(
            vehicleDao.addVehicleEntity(vehicleEntity)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableCompletableObserver() {
                    override fun onComplete() {

                        isVehicleAdded.postValue(true)
                    }

                    override fun onError(e: Throwable) {

                        e.message?.let { Log.e("Insertion Error", it) }
                    }
                })
        )

        return isVehicleAdded
    }
}