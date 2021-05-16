package com.example.turbocareassignment.api

import com.example.turbocareassignment.di.DaggerTurboCareComponent
import javax.inject.Inject

class TurboCareService {

    @Inject
    lateinit var api: TurboCareAPI

    init {
        DaggerTurboCareComponent.builder().build().inject(this)
    }

}