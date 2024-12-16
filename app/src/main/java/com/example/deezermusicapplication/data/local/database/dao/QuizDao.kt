package com.example.deezermusicapplication.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.deezermusicapplication.data.local.database.entity.QuizEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizDao {

    @Query("SELECT * FROM QuizEntity WHERE playlistId=:playlistId")
    fun getQuizByPlaylist(playlistId: Int) : Flow<List<QuizEntity>>

    @Query("SELECT COUNT(*) FROM QUIZENTITY WHERE playlistId=:playlistId")
    suspend fun getQuizCountByPlaylist(playlistId: Int) : Int

    @Query("DELETE FROM QUIZENTITY WHERE questionId = :questionId")
    suspend fun deleteQuestion(questionId: Int)

    @Insert
    suspend fun addQuestion(entity: QuizEntity)
}