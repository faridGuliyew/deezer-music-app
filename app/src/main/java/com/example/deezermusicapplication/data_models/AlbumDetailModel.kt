package com.example.deezermusicapplication.data_models

data class AlbumDetailModel(
    val albumId: String = "",
    val albumName: String = "",
    val albumCover: String = "",
    val releaseYear: String = "-",
    val contributors: List<ContributorModel> = emptyList(),
    val tracks: List<SearchResultItem> = emptyList()
)
