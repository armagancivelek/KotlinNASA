package com.armagancivelek.nasa.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.armagancivelek.nasa.data.model.MarsRoverPhotos
import com.armagancivelek.nasa.data.repository.Repository
import com.armagancivelek.nasa.utils.Rovers
import kotlinx.coroutines.launch

class CuriosityFeedViewModel(application: Application) : BaseViewModel(application) {
    private val TAG = "ABC"
    private var dataList: ArrayList<String?> = arrayListOf()
    private val repository = Repository()

    val photos = MutableLiveData<List<MarsRoverPhotos.Photo?>>()
    val error = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    //                    list?.forEach {
//                        dataList.add(it?.imgSrc)
//                    }
    fun refreshData() {
        getData()
    }

    fun getData() {
        launch {
            try {
                val response = repository.getPhotos(Rovers.curiosity, 1, "mast")
                val photosList = response.photos
                showPhotos(photosList)

            } catch (e: Exception) {

                Log.d(TAG, "${e.localizedMessage}")

                error.value = true
                loading.value = false
                Toast.makeText(getApplication(), "error :${e.localizedMessage}", Toast.LENGTH_LONG)
                    .show()


            }
        }
    }

    fun showPhotos(photosList: List<MarsRoverPhotos.Photo?>?) {


        error.value = false
        loading.value = false
        photos.value = photosList


    }


}