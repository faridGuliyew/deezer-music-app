package com.example.deezermusicapplication.presentation.screens.playlist_detail_screen

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.playlistDetailFragment() {
    composable(route = "playlistDetailFragment/{playlist_id}") {
        val viewModel: PlaylistDetailViewModel = viewModel()
        val playlist by viewModel.playlist.collectAsStateWithLifecycle()
        val tracks by viewModel.tracks.collectAsStateWithLifecycle()
        val relatedQuizCount by viewModel.relatedQuizCount.collectAsStateWithLifecycle()

        playlist?.let {
            PlaylistDetailScreen(
                playlist = it,
                tracks = tracks,
                relatedQuizCount = relatedQuizCount,
                onGoToTrack = viewModel::goToTrackDetails,
                onAddToQuiz = viewModel::goToQuizScreen,
                onRemoveTrack = viewModel::removeTrack,
                onGoToQuizListing = viewModel::onGoToQuizListing,
                onBackPressed = viewModel::onBackPressed
            )
        }
    }
}