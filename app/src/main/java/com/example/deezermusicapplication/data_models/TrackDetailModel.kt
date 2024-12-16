package com.example.deezermusicapplication.data_models

import androidx.compose.ui.graphics.Color

data class TrackDetailModel(
    val trackId: String,
    val trackName: String = "",
    val trackPreview: String = "",
    val backgroundColor: Color = Color.White,
    val albumCover: String = "",
    val releaseYear: String = "-",
    val albumName: String = "",
    val albumId: String = "",
    val contributors: List<ContributorModel> = emptyList()
)
