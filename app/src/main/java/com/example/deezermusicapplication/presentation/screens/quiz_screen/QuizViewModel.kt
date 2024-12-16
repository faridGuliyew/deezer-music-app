package com.example.deezermusicapplication.presentation.screens.quiz_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deezermusicapplication.data.local.database.AppDatabase
import com.example.deezermusicapplication.data.local.database.entity.QuizEntity
import com.example.deezermusicapplication.data.network.api.DeezerApi
import com.example.deezermusicapplication.media.MusicPlayer
import com.example.deezermusicapplication.navigation.Navigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class QuizViewModel (
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val playlistId = savedStateHandle.get<String>("playlist_id")?.toInt()!!
    val quizDao = AppDatabase.getInstance().getQuizDao()
    val deezerApi = DeezerApi.instance

    private val allQuizzes = MutableStateFlow<List<QuizEntity>>(emptyList())
    val games = MutableStateFlow<List<GameModel>>(emptyList())
    val correctQuestionCount = MutableStateFlow(0)
    val currentGameIndex = MutableStateFlow(0)
    val currentGame = MutableStateFlow<GameModel?>(null)
    val isQuizFinished = MutableStateFlow(false)

    init {
        viewModelScope.launch {
            // get all quizzes
            quizDao.getQuizByPlaylist(playlistId).collectLatest { quizzes->
                allQuizzes.update { quizzes }
                println("QuizViewModel - allQuizzes: $quizzes")
                // create games
                games.update { quizzes.map { GameModel(quiz = it) } }
                // start the quiz
                goToGame(index = 0)
            }
        }
    }

    private fun goToGame(index: Int) {
        if (index > games.value.lastIndex) return isQuizFinished.update { true }

        currentGameIndex.update { index }
        currentGame.update { games.value[index] }

        println("QuizViewModel - ${currentGame.value}, index: $index")

        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                try {
                    val trackPreview = deezerApi.getTrackDetails(trackId = allQuizzes.value[index].trackId).preview
                    MusicPlayer.setDataSource(trackPreview)
                    MusicPlayer.start()
                } catch (e:Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun goBack() {
        viewModelScope.launch {
            Navigator.navigate(Navigator.NavigationCommand(route = "back"))
        }
    }

    fun onOptionSelected(selectedOptionIndex: Int) {
        viewModelScope.launch {
            // Increase correct question count
            if (selectedOptionIndex == currentGame.value?.quiz?.correctOptionIndex) {
                correctQuestionCount.update { it + 1 }
            }
            // Highlight selection
            currentGame.update { old->
                old?.copy(selectedOptionIndex = selectedOptionIndex)
            }
            // REVEAL THE REAL ANSWER
            delay(500)
            currentGame.update { old->
                old?.copy(selectedOptionIndex = old.quiz.correctOptionIndex)
            }
            // SKIP
            delay(1000)
            goToGame(currentGameIndex.value + 1)
        }
    }

}

data class GameModel(
    val quiz: QuizEntity,
    val selectedOptionIndex: Int = 0,
    val isCorrect: Boolean = false
)