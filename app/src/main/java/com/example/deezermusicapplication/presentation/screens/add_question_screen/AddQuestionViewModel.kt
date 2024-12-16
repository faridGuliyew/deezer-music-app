package com.example.deezermusicapplication.presentation.screens.add_question_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.deezermusicapplication.data.local.database.AppDatabase
import com.example.deezermusicapplication.data.local.database.entity.QuizEntity
import com.example.deezermusicapplication.data.local.database.entity.TrackEntity
import com.example.deezermusicapplication.navigation.Navigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AddQuestionViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val playlistId : Int = savedStateHandle.get<String>("playlist_id")?.toInt()!!
    val trackId = savedStateHandle.get<String>("track_id")!!
    val playlistName = savedStateHandle.getStateFlow("playlist_name", "")
    lateinit var track: TrackEntity
    private val quizDao = AppDatabase.getInstance().getQuizDao()
    private val tracksDao = AppDatabase.getInstance().getTracksDao()

    init {
        println("AddQuestionViewModel - $playlistId")
        println("AddQuestionViewModel - $trackId")
//        println("AddQuestionViewModel - ${playlistName.value}")
        viewModelScope.launch {
            track = tracksDao.getTrack(id = trackId)
        }
    }

    fun addQuestion(
        question: String,
        optionA: String,
        optionB: String,
        optionC: String,
        optionD: String,
        correctOptionIndex: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            quizDao.addQuestion(
                QuizEntity(
                    playlistId = playlistId,
                    trackId = trackId,
                    trackPreview = track.preview,
                    question = question,
                    optionA = optionA,
                    optionB = optionB,
                    optionC = optionC,
                    optionD = optionD,
                    correctOptionIndex = correctOptionIndex
                )
            )
            goBack()
        }
    }

    fun goBack() {
        viewModelScope.launch {
            Navigator.navigate(
                Navigator.NavigationCommand(route = "back")
            )
        }
    }

}