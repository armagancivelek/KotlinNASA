package com.armagancivelek.nasa.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import com.armagancivelek.nasa.R
import com.armagancivelek.nasa.utils.downloadFromUrl
import com.armagancivelek.nasa.utils.placeholderProgressBar
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BottomSheetDialog : BottomSheetDialogFragment() {
    private val args: BottomSheetDialogArgs by navArgs()
    lateinit var photo: ImageView
    private lateinit var v: View


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_bottom_sheet_dialog, container, false)

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val mPhoto = args.photo

        v.findViewById<ImageView>(R.id.photo).apply {
            downloadFromUrl(
                mPhoto.imgSrc?.replace("http", "https"),
                placeholderProgressBar(v.context)
            )
        }
        v.findViewById<TextView>(R.id.photoDate).apply {
            text = "Tarih: ${mPhoto.earthDate}"

        }
        v.findViewById<TextView>(R.id.roverName).apply {
            text = "Araç: ${mPhoto.rover?.name}"
        }
        v.findViewById<TextView>(R.id.camera).apply {
            text = "Kamera: ${mPhoto.camera?.fullName}"

        }
        v.findViewById<TextView>(R.id.state).apply {
            text = "Görev Durumu: ${mPhoto.rover?.status}"

        }
        v.findViewById<TextView>(R.id.launchDate).apply {
            text = "Fırlatılma Tarihi: ${mPhoto.rover?.launchDate}"


        }
        v.findViewById<TextView>(R.id.landingDate).apply {
            text = "İniş Tarihi: ${mPhoto.rover?.landingDate}"

        }


    }

}