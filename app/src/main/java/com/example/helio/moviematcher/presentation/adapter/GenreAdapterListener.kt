package com.example.helio.moviematcher.presentation.adapter

import androidx.constraintlayout.widget.ConstraintLayout
import com.example.helio.moviematcher.data.response.GenreResponse

interface GenreAdapterListener {

    fun setOnClickGenre(genre: GenreResponse, textView: ConstraintLayout)
}