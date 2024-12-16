package com.example.deezermusicapplication.presentation.screens.album_detail_screen

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.deezermusicapplication.presentation.components.loading.LoadingDialog

fun NavGraphBuilder.albumDetailFragment() {

    composable(
        route = "albumDetailFragment/{album_id}"
    ) {
        val viewModel : AlbumDetailViewModel = viewModel()
        val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
        val albumDetails by viewModel.albumDetails.collectAsStateWithLifecycle()

        AlbumDetailScreen(
            albumDetails = albumDetails,
            onBackPressed = viewModel::goBack,
            onGoToTrack = viewModel::goToTrackDetails
        )

        LoadingDialog(isLoading = isLoading)
    }
}