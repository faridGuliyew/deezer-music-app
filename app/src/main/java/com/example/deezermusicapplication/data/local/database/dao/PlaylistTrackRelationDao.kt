package com.example.deezermusicapplication.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.deezermusicapplication.data.local.database.entity.PlaylistEntity
import com.example.deezermusicapplication.data.local.database.entity.TrackEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaylistTrackRelationDao {

    @Query("INSERT INTO playlisttrackrelationentity values (:playlistId, :trackId)")
    suspend fun addTrackToPlaylist(trackId: String, playlistId: Int)

    @Query("DELETE FROM playlisttrackrelationentity WHERE trackId=:trackId AND playlistId=:playlistId")
    suspend fun removeTrackFromPlaylist(trackId: String, playlistId: Int)

    @Query("DELETE FROM playlisttrackrelationentity where playlistId = :playlistId")
    suspend fun deletePlaylist(playlistId: Int)

    @Query("SELECT * FROM trackentity LEFT JOIN playlisttrackrelationentity pr using(trackId) WHERE pr.playlistId = :playlistId")
    fun getTracksForPlaylist(playlistId: Int) : Flow<List<TrackEntity>>

    @Query("SELECT * FROM playlistentity LEFT JOIN playlisttrackrelationentity pr using(playlistId) WHERE pr.trackId = :trackId")
    fun getPlaylistsForTrack(trackId: String) : Flow<List<PlaylistEntity>>

}