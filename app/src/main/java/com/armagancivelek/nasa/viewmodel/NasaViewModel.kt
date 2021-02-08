package com.armagancivelek.nasa.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.armagancivelek.nasa.data.model.MarsRoverPhotos.Photo
import com.armagancivelek.nasa.data.repository.Repository
import com.armagancivelek.nasa.utils.Rovers
import kotlinx.coroutines.launch

class NasaViewModel(application: Application) : BaseViewModel(application) {
    private val repository = Repository()
    val photos = MutableLiveData<List<Photo?>>()
    val error = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()
    var pageNumber = 1
    var currentResponse: MutableList<Photo>? = null
    fun refreshData(rover: Rovers, camera: String? = null) {
        getData(rover, camera)

    }

    fun getData(rover: Rovers, camera: String? = null) {
        loading.value = true
        launch {
            try {
                if (camera != null) {
                    currentResponse?.clear()
                    pageNumber = 1
                }


                val response = repository.getPhotos(rover, pageNumber, camera)
                response.let {
                    pageNumber++
                    if (currentResponse == null || currentResponse?.size == 0) {
                        currentResponse = response.photos
                    } else {
                        currentResponse!!.addAll(response.photos)

                    }
                }

                showPhotos(currentResponse)

            } catch (e: Exception) {
                error.value = true
                loading.value = false
                Toast.makeText(getApplication(), "error :${e.localizedMessage}", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    fun showPhotos(photosList: List<Photo?>?) {

        photosList?.let {

            loading.value = false
            error.value = false
            photos.value = photosList

        }


    }
}