package com.example.helio.moviematcher.data.response

import com.google.gson.annotations.SerializedName

class KeywordResult (
    @SerializedName("id") val id: Int,
    @SerializedName("keywords") val keywords: List<KeywordResponse>
)