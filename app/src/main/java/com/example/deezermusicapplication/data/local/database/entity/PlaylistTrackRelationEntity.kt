package com.example.deezermusicapplication.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (primaryKeys = ["playlistId", "trackId"])
data class PlaylistTrackRelationEntity(
    val playlistId: Int,
    val trackId: String
)
