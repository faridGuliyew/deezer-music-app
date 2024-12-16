package com.example.deezermusicapplication.presentation.screens.playlist_detail_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.deezermusicapplication.data.local.database.AppDatabase
import com.example.deezermusicapplication.data.local.database.entity.PlaylistEntity
import com.example.deezermusicapplication.data.local.database.entity.TrackEntity
import com.example.deezermusicapplication.navigation.Navigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlaylistDetailViewModel (
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val playlistId = savedStateHandle.get<String>("playlist_id")!!.toInt()
    private val playlistDao = AppDatabase.getInstance().getPlaylistDao()
    private val quizDao = AppDatabase.getInstance().getQuizDao()
    private val playlistTrackRelationDao = AppDatabase.getInstance().getPlaylistTrackRelationDao()
    val playlist = MutableStateFlow<PlaylistEntity?>(null)
    val tracks = MutableStateFlow<List<TrackEntity>>(emptyList())
    val relatedQuizCount = MutableStateFlow(0)

    init {
        getPlaylist()
        getTracks()
        getQuizCount()
    }

    private fun getPlaylist() {
        viewModelScope.launch (Dispatchers.IO) {
            playlist.update {
                playlistDao.getPlaylist(id = playlistId)
            }
        }
    }

    private fun getTracks() {
        viewModelScope.launch (Dispatchers.IO) {
            playlistTrackRelationDao.getTracksForPlaylist(playlistId).collectLatest { dbTracks->
                tracks.update { dbTracks }
            }
        }
    }

    private fun getQuizCount() {
        viewModelScope.launch (Dispatchers.IO) {
            relatedQuizCount.update {
                quizDao.getQuizCountByPlaylist(playlistId)
            }
        }
    }

    fun goToQuizScreen(trackId: String) {
        val playlist = playlist.value
        viewModelScope.launch {
            Navigator.navigate(Navigator.NavigationCommand(route = "add_question/${playlist!!.playlistName}/$playlistId/$trackId"))
        }
    }

    fun removeTrack(trackId: String) {
        viewModelScope.launch (Dispatchers.IO) {
            playlistTrackRelationDao.removeTrackFromPlaylist(trackId = trackId, playlistId = playlistId)
        }
    }

    fun onGoToQuizListing() {
        viewModelScope.launch {
            Navigator.navigate(Navigator.NavigationCommand(route = "quizListingFragment/$playlistId"))
        }
    }

    fun onBackPressed() {
        viewModelScope.launch {
            Navigator.navigate(Navigator.NavigationCommand(route = "back"))
        }
    }

    fun goToTrackDetails(id: String) {
        viewModelScope.launch {
            Navigator.navigate(Navigator.NavigationCommand(route = "trackDetailFragment/$id"))
        }
    }
}