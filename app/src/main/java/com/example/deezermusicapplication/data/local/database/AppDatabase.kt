package com.example.deezermusicapplication.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.deezermusicapplication.data.local.database.dao.PlaylistDao
import com.example.deezermusicapplication.data.local.database.dao.PlaylistTrackRelationDao
import com.example.deezermusicapplication.data.local.database.dao.QuizDao
import com.example.deezermusicapplication.data.local.database.dao.TracksDao
import com.example.deezermusicapplication.data.local.database.entity.PlaylistEntity
import com.example.deezermusicapplication.data.local.database.entity.PlaylistTrackRelationEntity
import com.example.deezermusicapplication.data.local.database.entity.QuizEntity
import com.example.deezermusicapplication.data.local.database.entity.TrackEntity

@Database(entities = [PlaylistEntity::class, TrackEntity::class, PlaylistTrackRelationEntity::class, QuizEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getQuizDao() : QuizDao

    abstract fun getPlaylistDao() : PlaylistDao

    abstract fun getTracksDao() : TracksDao

    abstract fun getPlaylistTrackRelationDao() : PlaylistTrackRelationDao


    companion object {
        private var instance: AppDatabase? = null
        fun init(context: Context) {
            if (instance != null) return

            instance = Room.databaseBuilder(
                context = context,
                klass = AppDatabase::class.java,
                name = "AppDatabase"
            ).build()
        }
        fun getInstance() = instance ?: error("Initialize the database, first")
    }
}