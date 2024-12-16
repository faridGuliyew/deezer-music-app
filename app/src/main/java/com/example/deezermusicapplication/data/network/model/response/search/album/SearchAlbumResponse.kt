package com.example.deezermusicapplication.data.network.model.response.search.album


import com.google.gson.annotations.SerializedName

data class SearchAlbumResponse(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("next")
    val next: String,
    @SerializedName("total")
    val total: Int
)