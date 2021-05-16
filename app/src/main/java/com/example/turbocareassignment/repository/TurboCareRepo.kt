package com.example.turbocareassignment.repository

import com.example.turbocareassignment.api.TurboCareService
import com.example.turbocareassignment.di.DaggerTurboCareComponent
import io.reactivex.Single
import javax.inject.Inject

class TurboCareRepo {

    @Inject
    lateinit var turboCareService: TurboCareService

    init {
        DaggerTurboCareComponent.builder().build().inject(this)
    }

    fun get2wList(): Single<List<String>> {

        return turboCareService.api.get2wList()
    }

    fun get2wModelsList(make: String): Single<List<String>> {

        return turboCareService.api.get2wModelsList(make)
    }

    fun get4wList(): Single<List<String>> {

        return turboCareService.api.get4wList()
    }

    fun get4wModelsList(make: String): Single<List<String>> {

        return turboCareService.api.get4wModelsList(make)
    }
}