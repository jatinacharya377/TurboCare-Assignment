package com.example.turbocareassignment.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface TurboCareAPI {

    @GET("makes?class=2w")
    fun get2wList(): Single<List<String>>
    @GET("models?class=2w")
    fun get2wModelsList(
        @Query("make") make: String
    ): Single<List<String>>
    @GET("makes?class=4w")
    fun get4wList(): Single<List<String>>
    @GET("models?class=4w")
    fun get4wModelsList(
        @Query("make") make: String
    ): Single<List<String>>
}