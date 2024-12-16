package com.example.deezermusicapplication.presentation.screens.search_screen

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.deezermusicapplication.presentation.components.loading.LoadingDialog

fun NavGraphBuilder.searchFragment() {
    composable(route = "searchFragment") {
        val viewModel : SearchViewModel = viewModel()
        // observe states in vm here
        val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
        val searchResult by viewModel.searchResult.collectAsStateWithLifecycle()
        val searchValue by viewModel.searchValue.collectAsStateWithLifecycle()
        val selectedSearchFilter by viewModel.selectedSearchType.collectAsStateWithLifecycle()
        val isPlaylistDialogVisible by viewModel.isPlaylistDialogVisible.collectAsStateWithLifecycle()
        val allPlaylists by viewModel.allPlaylists.collectAsStateWithLifecycle()
        val addedPlaylists by viewModel.addedPlaylists.collectAsStateWithLifecycle()

        SearchScreen(
            isLoading = isLoading,
            searchResult = searchResult,
            searchValue = searchValue,
            selectedSearchType = selectedSearchFilter,
            onSearchFilterChanged = viewModel::onSearchFilterChanged,
            onSearchValueChanged = viewModel::onSearchValueChanged,
            onSearchResultClicked = viewModel::onSearchResultClicked,
            onGoToPlaylist = viewModel::onGoToPlaylist,
            onShowPlaylistDialog = viewModel::showPlaylistDialog,
            isPlaylistDialogVisible = isPlaylistDialogVisible,
            onTogglePlaylist = viewModel::togglePlaylist,
            onSearch = viewModel::onSearch,
            onHidePlaylistDialog = viewModel::hidePlaylistDialog,
            allPlaylists = allPlaylists,
            addedPlaylists = addedPlaylists
        )

        LoadingDialog(isLoading = isLoading)
    }
}