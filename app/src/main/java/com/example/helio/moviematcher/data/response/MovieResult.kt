package com.example.helio.moviematcher.data.response

import com.google.gson.annotations.SerializedName

class MovieResult (
    @SerializedName("page") val page: Int,
    @SerializedName("results") val movies: List<MovieResponse>,
    @SerializedName("total_pages") val pages: Int
)