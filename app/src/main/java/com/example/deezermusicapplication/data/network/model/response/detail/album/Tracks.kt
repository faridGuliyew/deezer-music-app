package com.example.deezermusicapplication.data.network.model.response.detail.album


import com.google.gson.annotations.SerializedName

data class Tracks(
    @SerializedName("data")
    val `data`: List<DataX>
)