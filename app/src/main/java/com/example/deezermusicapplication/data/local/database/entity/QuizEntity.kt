package com.example.deezermusicapplication.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class QuizEntity (
    @PrimaryKey(autoGenerate = true) val questionId: Int = 0,
    val playlistId: Int,
    val trackId: String,
    val trackPreview: String,
    val question: String,
    val optionA: String,
    val optionB: String,
    val optionC: String,
    val optionD: String,
    val correctOptionIndex: Int
)