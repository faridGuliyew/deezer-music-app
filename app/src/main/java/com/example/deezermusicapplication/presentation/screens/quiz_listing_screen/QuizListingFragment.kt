package com.example.deezermusicapplication.presentation.screens.quiz_listing_screen

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.quizListingFragment() {
    composable(route = "quizListingFragment/{playlist_id}") {

        val viewModel : QuizListingViewModel = viewModel()
        val allQuizzes by viewModel.allQuizzes.collectAsStateWithLifecycle()


        QuizListingScreen(
            allQuizzes = allQuizzes,
            onPlayPreview = viewModel::playPreview,
            onRemoveQuestion = viewModel::removeQuestion,
            onBackPressed = viewModel::goBack,
            onPlayQuiz = viewModel::playQuiz
        )
    }
}