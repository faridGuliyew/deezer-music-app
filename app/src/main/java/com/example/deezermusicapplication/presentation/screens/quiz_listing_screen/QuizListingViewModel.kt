package com.example.deezermusicapplication.presentation.screens.quiz_listing_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deezermusicapplication.data.local.database.AppDatabase
import com.example.deezermusicapplication.data.local.database.entity.QuizEntity
import com.example.deezermusicapplication.data.network.api.DeezerApi
import com.example.deezermusicapplication.media.MusicPlayer
import com.example.deezermusicapplication.navigation.Navigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QuizListingViewModel (
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val playlistId = savedStateHandle.get<String>("playlist_id")?.toInt()!!
    private val quizDao = AppDatabase.getInstance().getQuizDao()
    val deezerApi = DeezerApi.instance
    val allQuizzes = MutableStateFlow<List<QuizEntity>>(emptyList())

    init {
        getQuizzes()
    }

    private fun getQuizzes() {
        viewModelScope.launch {
            quizDao.getQuizByPlaylist(playlistId).collectLatest { quizzes->
                allQuizzes.update { quizzes }
            }
        }
    }

    fun playPreview(trackId: String) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                val trackPreview = deezerApi.getTrackDetails(trackId = trackId).preview
                runCatching {
                    MusicPlayer.setDataSource(trackPreview)
                    MusicPlayer.start()
                }
            }
        }
    }

    fun removeQuestion(questionId: Int) {
        viewModelScope.launch (Dispatchers.IO) {
            quizDao.deleteQuestion(questionId)
        }
    }

    fun goBack() {
        viewModelScope.launch {
            Navigator.navigate(Navigator.NavigationCommand(route = "back"))
        }
    }

    fun playQuiz() {
        viewModelScope.launch {
            Navigator.navigate(Navigator.NavigationCommand(route = "quizFragment/$playlistId"))
        }
    }


}