package com.armagancivelek.nasa.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NasaApiService {

    private val BASE_URL = "https://api.nasa.gov/mars-photos/api/v1/rovers/"

    val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api by lazy {
        retrofit.create(NasaApi::class.java)
    }
}