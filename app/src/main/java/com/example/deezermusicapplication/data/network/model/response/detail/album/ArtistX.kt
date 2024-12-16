package com.example.deezermusicapplication.data.network.model.response.detail.album


import com.google.gson.annotations.SerializedName

data class ArtistX(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("tracklist")
    val tracklist: String,
    @SerializedName("type")
    val type: String
)