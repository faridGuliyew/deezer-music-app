package com.example.deezermusicapplication.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.deezermusicapplication.data.local.database.entity.PlaylistEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaylistDao {

    @Insert
    suspend fun addPlaylist(playlistEntity: PlaylistEntity)

    @Query("SELECT * FROM playlistentity")
    fun getPlaylists() : Flow<List<PlaylistEntity>>

    @Query("SELECT * FROM playlistentity WHERE playlistId = :id")
    fun getPlaylist(id: Int) : PlaylistEntity

    @Query("DELETE FROM playlistentity WHERE playlistId = :id")
    suspend fun deletePlaylist(id: Int)
}