package com.example.deezermusicapplication.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.deezermusicapplication.data.local.database.entity.PlaylistEntity
import com.example.deezermusicapplication.data.local.database.entity.TrackEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TracksDao {

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTrack(trackEntity: TrackEntity)

    @Query("SELECT * FROM trackentity")
    fun getTracks() : Flow<List<TrackEntity>>

    @Query("SELECT * FROM trackentity WHERE trackId=:id")
    suspend fun getTrack(id: String) : TrackEntity

    @Query("DELETE FROM trackentity WHERE trackId = :trackId")
    suspend fun deleteTrack(trackId: String)
}