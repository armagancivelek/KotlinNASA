package com.armagancivelek.nasa.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.armagancivelek.nasa.data.model.MarsRoverPhotos
import com.armagancivelek.nasa.databinding.ItemRowBinding
import com.armagancivelek.nasa.utils.downloadFromUrl
import com.armagancivelek.nasa.utils.placeholderProgressBar


class NasaAdapter(private val photosList: ArrayList<MarsRoverPhotos.Photo>) :
    RecyclerView.Adapter<NasaAdapter.ViewHolder>() {


    inner class ViewHolder(val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {


        return ViewHolder(
            ItemRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.imageView.downloadFromUrl(
            photosList[position].imgSrc?.replace("http", "https"),
            placeholderProgressBar(holder.itemView.context)
        )
        holder.binding.imageView.setOnClickListener {
            onItemClik?.invoke(position, photosList[position])

        }


    }

    override fun getItemCount(): Int {
        return photosList.size
    }

    fun updatePhotos(newPhotosList: List<MarsRoverPhotos.Photo>) {
        photosList.clear()
        photosList.addAll(newPhotosList)
        notifyDataSetChanged()

    }

    private var onItemClik: ((Int, MarsRoverPhotos.Photo) -> Unit)? = null

    fun setOnClickListener(listener: (Int, MarsRoverPhotos.Photo) -> Unit) {
        onItemClik = listener
    }


}