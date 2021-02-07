package com.armagancivelek.nasa.data.repository

import com.armagancivelek.nasa.data.api.NasaApiService
import com.armagancivelek.nasa.data.model.MarsRoverPhotos
import com.armagancivelek.nasa.utils.Rovers


class Repository {

    suspend fun getPhotos(roverName: Rovers, page: Int, camera: String? = null): MarsRoverPhotos {

        return when (roverName) {
            Rovers.curiosity -> NasaApiService.api.getCuriosity(page, camera)
            Rovers.opportunity -> NasaApiService.api.getOpportunity(page, camera)
            Rovers.spirit -> NasaApiService.api.getSpirit(page, camera)
        }

    }
}