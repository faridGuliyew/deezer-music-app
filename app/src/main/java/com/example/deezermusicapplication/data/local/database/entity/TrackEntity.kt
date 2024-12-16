package com.example.deezermusicapplication.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TrackEntity(
    @PrimaryKey val trackId: String,
    val name: String,
    val artist: String,
    val preview: String,
    val photo: String?
)
