package com.example.turbocareassignment.di

import com.example.turbocareassignment.api.TurboCareAPI
import com.example.turbocareassignment.api.TurboCareService
import com.example.turbocareassignment.repository.TurboCareRepo
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class TurboCareModule {

    private val baseUrl = "https://test.turbocare.app/turbo/care/v1/"

    @Provides
    fun provideTurboCareAPI(): TurboCareAPI {

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(TurboCareAPI::class.java)
    }

    @Provides
    fun provideTurboCareService(): TurboCareService {

        return TurboCareService()
    }

    @Provides
    fun provideTurboCareRepo(): TurboCareRepo {

        return TurboCareRepo()
    }
}