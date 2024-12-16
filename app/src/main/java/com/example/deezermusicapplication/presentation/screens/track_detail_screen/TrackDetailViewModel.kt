package com.example.deezermusicapplication.presentation.screens.track_detail_screen

import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.example.deezermusicapplication.data.local.database.AppDatabase
import com.example.deezermusicapplication.data.local.database.entity.PlaylistEntity
import com.example.deezermusicapplication.data.local.database.entity.TrackEntity
import com.example.deezermusicapplication.data_models.ContributorModel
import com.example.deezermusicapplication.data_models.TrackDetailModel
import com.example.deezermusicapplication.media.MusicPlayer
import com.example.deezermusicapplication.navigation.Navigator
import com.example.deezermusicapplication.data.network.api.DeezerApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.URL


class TrackDetailViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val deezerApi = DeezerApi.instance
    private val trackId: String = savedStateHandle["track_id"] ?: ""
    private val tracksDao = AppDatabase.getInstance().getTracksDao()
    private val playlistDao = AppDatabase.getInstance().getPlaylistDao()
    private val playlistTrackRelationDao = AppDatabase.getInstance().getPlaylistTrackRelationDao()
    val allPlaylists = MutableStateFlow<List<PlaylistEntity>>(emptyList())
    val addedPlaylists =  MutableStateFlow<List<PlaylistEntity>>(emptyList())


    val isLoading = MutableStateFlow(false)
    val isMusicPlaying = MutableStateFlow(false)
    val trackDetails = MutableStateFlow(TrackDetailModel(trackId = ""))
    val playTime = MutableStateFlow(0)

    private var isMusicNotPrepared = true



    init {
        getTrackDetails()
        getPlaylists()
        getAddedPlaylists()
    }

    private fun getPlaylists() {
        viewModelScope.launch (Dispatchers.IO) {
            playlistDao.getPlaylists().collectLatest { dbPlaylists->
                allPlaylists.update {
                    dbPlaylists
                }
            }
        }
    }

    private fun getAddedPlaylists() {
        viewModelScope.launch (Dispatchers.IO) {
            playlistTrackRelationDao.getPlaylistsForTrack(trackId = trackId).collectLatest { dbPlaylists->
                addedPlaylists.update {
                    dbPlaylists
                }
            }
        }
    }

    private fun getTrackDetails() {
        viewModelScope.launch {
            isLoading.update { true }
            try {
                val detailResponse = deezerApi.getTrackDetails(trackId = trackId)
                Log.i("TrackDetailViewModel", detailResponse.toString())
                // Code to get background color dynamically from an image bitmap
                val coverBitmap = try {
                    val url = URL(detailResponse.album.coverBig)
                    withContext(Dispatchers.IO) {
                        return@withContext BitmapFactory.decodeStream(url.openConnection().getInputStream())
                    }
                } catch (e: IOException) {
                    null
                }
                val backgroundColor = (if (coverBitmap != null)
                    Color(
                        Palette.from(coverBitmap).generate().getDominantColor("#FFFFFF".toColorInt())
                    ).copy(0.5f)
                else Color.White).also {
                    Log.i("TrackDetailViewModel", "Background color: $it")
                }


                val mappedTrackDetail =
                    TrackDetailModel(
                        trackId = detailResponse.id,
                        trackName = detailResponse.title,
                        trackPreview = detailResponse.preview,
                        albumCover = detailResponse.album.coverBig,
                        releaseYear = detailResponse.releaseDate.split("-").firstOrNull() ?: "-",
                        albumName = detailResponse.album.title,
                        albumId = detailResponse.album.id,
                        contributors = detailResponse.contributors.map {
                            ContributorModel(
                                name = it.name,
                                role = it.role,
                                type = it.type,
                                photo = it.picture,
                                linkToProfile = it.link
                            )
                        },
                        backgroundColor = backgroundColor
                    )

                trackDetails.update { mappedTrackDetail }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading.update { false }
            }
        }
    }

    fun goBack() {
        viewModelScope.launch {
            Navigator.navigate(Navigator.NavigationCommand(route = "back"))
        }
    }

    fun togglePlaylist(playlistId: Int) {
        val currentTrack = trackDetails.value
        viewModelScope.launch (Dispatchers.IO) {
            // Add track to list of all saved tracks, first
            tracksDao.addTrack(
                trackEntity = TrackEntity(
                    trackId = currentTrack.trackId,
                    name = currentTrack.trackName,
                    artist = currentTrack.contributors.firstOrNull()?.name ?: "-",
                    photo = currentTrack.albumCover,
                    preview = currentTrack.trackPreview
                )
            )
            // Check if track was already added to the playlist
            if (playlistId in addedPlaylists.value.map { it.playlistId }) {
                // Remove track from playlist
                playlistTrackRelationDao.removeTrackFromPlaylist(
                    trackId = trackId, playlistId = playlistId
                )
            } else {
                // Add track to playlist
                playlistTrackRelationDao.addTrackToPlaylist(
                    trackId = currentTrack.trackId,
                    playlistId = playlistId
                )
            }
        }
    }

    fun goToAlbumDetails() {
        viewModelScope.launch {
            Navigator.navigate(Navigator.NavigationCommand(route = "albumDetailFragment/${trackDetails.value.albumId}"))
        }
    }

    fun playSong() {
        // set new music
        if (isMusicNotPrepared) {
            // Show loading
            viewModelScope.launch {
//                isLoading.emit(true)
                withContext(Dispatchers.Default) {
                    runCatching {
                        MusicPlayer.setDataSource(trackDetails.value.trackPreview)
                        MusicPlayer.start()
                    }
                }
                // End of loading
//                isLoading.emit(false)
            }
            isMusicNotPrepared = false
        }
        else {
            MusicPlayer.start()
        }
        isMusicPlaying.update { true }
    }

    fun pauseSong() {
        MusicPlayer.pause()
        isMusicPlaying.update { false }
    }

    fun seekMusic(value: Float) {

    }

}