package com.armagancivelek.nasa.views.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.armagancivelek.nasa.R


class CuriosityAdapter : RecyclerView.Adapter<CuriosityAdapter.ViewHolder>() {

    val curiosityList = ArrayList<String>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)

        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return curiosityList.size
    }

}