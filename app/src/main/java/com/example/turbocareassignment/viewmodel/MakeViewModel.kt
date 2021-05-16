package com.example.turbocareassignment.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.turbocareassignment.di.DaggerTurboCareComponent
import com.example.turbocareassignment.repository.TurboCareRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MakeViewModel: ViewModel() {

    @Inject
    lateinit var turboCareRepo: TurboCareRepo
    private val disposable = CompositeDisposable()
    private var fourWheelerList = MutableLiveData<List<String>>()
    private var fourWheelerModelsList = MutableLiveData<List<String>>()
    private var twoWheelerList = MutableLiveData<List<String>>()
    private var twoWheelerModelsList = MutableLiveData<List<String>>()

    init {
        DaggerTurboCareComponent.builder().build().inject(this)
    }

    fun get2wList(): LiveData<List<String>> {

        disposable.add(
            turboCareRepo.get2wList()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<String>>() {

                    override fun onSuccess(value: List<String>) {

                        twoWheelerList.postValue(value)
                    }

                    override fun onError(e: Throwable) {

                        e.message?.let { Log.e("Two Wheeler Error", it) }
                    }
                })
        )

        return twoWheelerList
    }

    fun get2wModelsList(make: String): LiveData<List<String>> {

        disposable.add(
            turboCareRepo.get2wModelsList(make)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<String>>() {

                    override fun onSuccess(value: List<String>) {

                        twoWheelerModelsList.postValue(value)
                    }

                    override fun onError(e: Throwable) {

                        e.message?.let { Log.e("Two Wheeler Model Error", it) }
                    }
                })
        )

        return twoWheelerModelsList
    }

    fun get4wList(): LiveData<List<String>> {

        disposable.add(
            turboCareRepo.get4wList()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<String>>() {

                    override fun onSuccess(value: List<String>) {

                        fourWheelerList.postValue(value)
                    }

                    override fun onError(e: Throwable) {

                        e.message?.let { Log.e("Four Wheeler Error", it) }
                    }
                })
        )

        return fourWheelerList
    }

    fun get4wModelsList(make: String): LiveData<List<String>> {

        disposable.add(
            turboCareRepo.get4wModelsList(make)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<String>>() {

                    override fun onSuccess(value: List<String>) {

                        fourWheelerModelsList.postValue(value)
                    }

                    @SuppressLint("LongLogTag")
                    override fun onError(e: Throwable) {

                        e.message?.let { Log.e("Four Wheeler Model Error", it) }
                    }
                })
        )

        return fourWheelerModelsList
    }

    override fun onCleared() {
        super.onCleared()

        disposable.clear()
    }
}