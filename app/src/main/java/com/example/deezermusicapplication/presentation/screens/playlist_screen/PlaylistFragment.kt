package com.example.deezermusicapplication.presentation.screens.playlist_screen

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.playlistFragment() {

    composable(route = "playlistFragment") {
        val viewModel = viewModel<PlaylistViewModel>()
        val playlists by viewModel.playlists.collectAsStateWithLifecycle()

        PlaylistScreen(
            onBackPressed = viewModel::onBackPressed,
            onAddPlaylist = viewModel::addPlaylist,
            onDeletePlaylist = viewModel::deletePlaylist,
            playlists = playlists,
            onPlaylistClicked = viewModel::goToPlaylistDetails
        )
    }

}