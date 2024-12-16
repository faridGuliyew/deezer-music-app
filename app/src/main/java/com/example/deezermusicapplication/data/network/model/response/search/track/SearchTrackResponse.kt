package com.example.deezermusicapplication.data.network.model.response.search.track


import com.google.gson.annotations.SerializedName

data class SearchTrackResponse(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("next")
    val next: String,
    @SerializedName("total")
    val total: Int
)