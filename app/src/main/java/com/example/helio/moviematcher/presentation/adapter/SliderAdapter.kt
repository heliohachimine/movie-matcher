package com.example.helio.moviematcher.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.helio.moviematcher.R


class SliderAdapter : RecyclerView.Adapter<SliderViewHolder>() {

    private var images: List<String> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.adapter_slider, parent, false)
        return SliderViewHolder(
            view
        )
    }

    override fun getItemCount(): Int = this.images.size

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.bind(images[position])

    }

    fun setList(images: List<String>) {
        this.images = images
        notifyDataSetChanged()
    }
}

class SliderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val imageView: ImageView = itemView.findViewById(R.id.img_slider)

    fun bind(image: String) {
        Glide.with(itemView).load(image).into(imageView)
    }
}