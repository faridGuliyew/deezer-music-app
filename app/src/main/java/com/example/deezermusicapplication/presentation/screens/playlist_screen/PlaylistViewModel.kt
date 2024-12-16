package com.example.deezermusicapplication.presentation.screens.playlist_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deezermusicapplication.data.local.database.AppDatabase
import com.example.deezermusicapplication.data.local.database.entity.PlaylistEntity
import com.example.deezermusicapplication.navigation.Navigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlaylistViewModel : ViewModel() {

    private val playlistDao = AppDatabase.getInstance().getPlaylistDao()
    private val playlistTrackRelationDao = AppDatabase.getInstance().getPlaylistTrackRelationDao()
    val playlists = MutableStateFlow<List<PlaylistEntity>>(emptyList())

    init {
        getPlaylists()
    }

    private fun getPlaylists() {
        viewModelScope.launch (Dispatchers.IO) {
            playlistDao.getPlaylists().collectLatest { dbPlaylists->
                playlists.update {
                    dbPlaylists
                }
            }
        }
    }


    fun addPlaylist(
        name: String,
        description: String,
        link: String
    ) {
        if (name.isEmpty()) return
        viewModelScope.launch (Dispatchers.IO) {
            playlistDao.addPlaylist(
                playlistEntity = PlaylistEntity(
                    playlistName = name,
                    playlistDescription = description,
                    playlistIconLink = link
                )
            )
        }
    }

    fun deletePlaylist(
        id: Int
    ) {
        viewModelScope.launch (Dispatchers.IO) {
            playlistDao.deletePlaylist(id)
            playlistTrackRelationDao.deletePlaylist(id)
        }
    }

    fun onBackPressed() {
        viewModelScope.launch {
            Navigator.navigate(Navigator.NavigationCommand(route = "back"))
        }
    }

    fun goToPlaylistDetails(id: Int) {
        viewModelScope.launch {
            Navigator.navigate(Navigator.NavigationCommand(route = "playlistDetailFragment/$id"))
        }
    }
}