package com.armagancivelek.nasa.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.armagancivelek.nasa.R
import com.armagancivelek.nasa.databinding.FragmentBottomSheetDialogBinding
import com.armagancivelek.nasa.utils.downloadFromUrl
import com.armagancivelek.nasa.utils.placeholderProgressBar
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BottomSheetDialog : BottomSheetDialogFragment() {
    private val args: BottomSheetDialogArgs by navArgs()
    private lateinit var binding: FragmentBottomSheetDialogBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom_sheet_dialog, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentBottomSheetDialogBinding.bind(view)
        val mPhoto = args.photo

        binding.photo.apply {
            downloadFromUrl(
                mPhoto.imgSrc?.replace("http", "https"),
                placeholderProgressBar(view.context)
            )
        }
        binding.photoDate.apply {
            text = "Tarih: ${mPhoto.earthDate}"

        }
        binding.roverName.apply {
            text = "Araç: ${mPhoto.rover?.name}"
        }
        binding.camera.apply {
            text = "Kamera: ${mPhoto.camera?.fullName}"

        }
        binding.state.apply {
            text = "Görev Durumu: ${mPhoto.rover?.status}"

        }
        binding.launchDate.apply {
            text = "Fırlatılma Tarihi: ${mPhoto.rover?.launchDate}"


        }
        binding.landingDate.apply {
            text = "İniş Tarihi: ${mPhoto.rover?.landingDate}"

        }


    }

}