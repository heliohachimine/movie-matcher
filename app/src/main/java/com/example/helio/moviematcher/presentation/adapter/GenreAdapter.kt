package com.example.helio.moviematcher.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.helio.moviematcher.R
import com.example.helio.moviematcher.data.response.GenreResponse

class GenreAdapter : RecyclerView.Adapter<GenreViewHolder>() {

    private var genres: List<GenreResponse> = ArrayList()
    var genreAdapterListener: GenreAdapterListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.adapter_genre, parent, false)
        return GenreViewHolder(
            view
        )
    }

    override fun getItemCount(): Int = this.genres.size

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bind(genres[position])
        holder.item.setOnClickListener {
            genreAdapterListener?.setOnClickGenre(genres[position], holder.item)
        }
    }

    fun setList(genres: List<GenreResponse>) {
        this.genres = genres
        notifyDataSetChanged()
    }
}

class GenreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val item: ConstraintLayout = itemView.findViewById(R.id.frame)
    val name: TextView = itemView.findViewById(R.id.genre_name)

    fun bind(genre: GenreResponse) {
        name.text = genre.name
    }
}