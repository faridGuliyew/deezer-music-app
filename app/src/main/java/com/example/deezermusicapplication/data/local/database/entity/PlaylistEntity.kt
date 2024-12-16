package com.example.deezermusicapplication.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PlaylistEntity(
    @PrimaryKey (autoGenerate = true) val playlistId: Int = 0,
    val playlistName: String,
    val playlistDescription: String,
    val playlistIconLink: String
)
