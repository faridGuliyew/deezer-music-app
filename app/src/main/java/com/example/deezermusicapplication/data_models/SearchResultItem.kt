package com.example.deezermusicapplication.data_models

data class SearchResultItem(
    val name: String,
    val artist: String,
    val photo: Any?,
    val type: SearchType,
    val id: String
)