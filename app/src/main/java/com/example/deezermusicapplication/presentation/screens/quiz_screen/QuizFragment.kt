package com.example.deezermusicapplication.presentation.screens.quiz_screen

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.quizFragment() {

    composable("quizFragment/{playlist_id}") {

        val viewModel : QuizViewModel = viewModel()
        val currentGame by viewModel.currentGame.collectAsStateWithLifecycle()
        val allGames by viewModel.games.collectAsStateWithLifecycle()
        val currentGameIndex by viewModel.currentGameIndex.collectAsStateWithLifecycle()
        val isQuizFinished by viewModel.isQuizFinished.collectAsStateWithLifecycle()
        val correctQuestionCount by viewModel.correctQuestionCount.collectAsStateWithLifecycle()


        QuizScreen(
            allGames = allGames,
            currentGameIndex = currentGameIndex,
            currentGame = currentGame,
            isQuizFinished = isQuizFinished,
            correctQuestionCount = correctQuestionCount,
            onBackPressed = viewModel::goBack,
            onOptionSelected = viewModel::onOptionSelected
        )
    }
}