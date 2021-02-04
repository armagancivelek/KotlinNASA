package com.armagancivelek.nasa.data.api

import com.armagancivelek.nasa.data.model.MarsRoverPhotos
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApi {


    @GET("curiosity/photos?sol=1000&api_key=DEMO_KEY")
    suspend fun getCuriosity(
      @Query("page") page: Int,
      @Query("camera") camera: String


    ): MarsRoverPhotos

    @GET("spirit/photos?sol=1000&api_key=DEMO_KEY")
    suspend fun getSpirit(
      @Query("page") page: Int,
      @Query("camera") camera: String

    ): MarsRoverPhotos

    @GET("opportunity/photos?sol=1000&api_key=DEMO_KEY")
    suspend fun getOpportunity(
      @Query("page") page: Int,
      @Query("camera") camera: String

    ): MarsRoverPhotos


}