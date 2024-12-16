package com.example.deezermusicapplication.presentation.screens.track_detail_screen

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.deezermusicapplication.presentation.components.loading.LoadingDialog

fun NavGraphBuilder.trackDetailFragment() {

    composable(
        route = "trackDetailFragment/{track_id}"
    ) {
        val viewModel : TrackDetailViewModel = viewModel()
        val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
        val isMusicPlaying by viewModel.isMusicPlaying.collectAsStateWithLifecycle()
        val playTime by viewModel.playTime.collectAsStateWithLifecycle()
        val trackDetails by viewModel.trackDetails.collectAsStateWithLifecycle()
        val allPlaylists by viewModel.allPlaylists.collectAsStateWithLifecycle()
        val addedPlaylists by viewModel.addedPlaylists.collectAsStateWithLifecycle()

        TrackDetailScreen(
            trackDetails = trackDetails,
            allPlaylists = allPlaylists,
            addedPlaylists = addedPlaylists,
            isMusicPlaying = isMusicPlaying,
            playTime = playTime,
            onBackPressed = viewModel::goBack,
            onTogglePlaylist = viewModel::togglePlaylist,
            onGoToAlbum = viewModel::goToAlbumDetails,
            onStartMusic = viewModel::playSong,
            onPauseMusic = viewModel::pauseSong,
            onSeekMusic = viewModel::seekMusic
        )

        LoadingDialog(isLoading = isLoading)
    }
}