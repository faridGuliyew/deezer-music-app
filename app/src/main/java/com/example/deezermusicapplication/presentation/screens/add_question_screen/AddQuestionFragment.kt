package com.example.deezermusicapplication.presentation.screens.add_question_screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute

fun NavGraphBuilder.addQuestionFragment() {

    composable("add_question/{playlist_name}/{playlist_id}/{track_id}") {
        val viewModel : AddQuestionViewModel = viewModel()
        val playlistName by viewModel.playlistName.collectAsStateWithLifecycle()

        AddQuestionScreen(
            playlistName = playlistName,
            onAddQuestion = viewModel::addQuestion,
            onBackPressed = viewModel::goBack
        )
    }
}