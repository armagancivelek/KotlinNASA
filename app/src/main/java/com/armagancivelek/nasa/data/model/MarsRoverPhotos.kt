package com.armagancivelek.nasa.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class MarsRoverPhotos(
    @SerializedName("photos")
    val photos: MutableList<Photo>
) {
    data class Photo(
        @SerializedName("camera")
        val camera: Camera?,
        @SerializedName("earth_date")
        val earthDate: String?,
        @SerializedName("id")
        val id: Int?,
        @SerializedName("img_src")
        val imgSrc: String?,
        @SerializedName("rover")
        val rover: Rover?,
        @SerializedName("sol")
        val sol: Int?
    ) : Serializable {
        data class Camera(
            @SerializedName("full_name")
            val fullName: String?,
            @SerializedName("id")
            val id: Int?,
            @SerializedName("name")
            val name: String?,
            @SerializedName("rover_id")
            val roverİd: Int?
        )

        data class Rover(
            @SerializedName("id")
            val id: Int?,
            @SerializedName("landing_date")
            val landingDate: String?,
            @SerializedName("launch_date")
            val launchDate: String?,
            @SerializedName("name")
            val name: String?,
            @SerializedName("status")
            val status: String?
        )
    }
}