package com.armagancivelek.nasa.views.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.armagancivelek.nasa.R
import com.armagancivelek.nasa.data.model.MarsRoverPhotos
import com.armagancivelek.nasa.utils.downloadFromUrl
import com.armagancivelek.nasa.utils.placeholderProgressBar
import kotlinx.android.synthetic.main.item_row.view.*


class NasaAdapter(private val photosList: ArrayList<MarsRoverPhotos.Photo>) :
    RecyclerView.Adapter<NasaAdapter.ViewHolder>() {


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)

        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.image_view.downloadFromUrl(
            photosList[position].imgSrc?.replace("http", "https"),
            placeholderProgressBar(holder.itemView.context)
        )

        holder.itemView.setOnClickListener {
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