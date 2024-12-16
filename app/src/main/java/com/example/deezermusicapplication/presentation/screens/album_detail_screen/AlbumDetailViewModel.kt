package com.example.deezermusicapplication.presentation.screens.album_detail_screen

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deezermusicapplication.data_models.AlbumDetailModel
import com.example.deezermusicapplication.data_models.ContributorModel
import com.example.deezermusicapplication.data_models.SearchResultItem
import com.example.deezermusicapplication.data_models.SearchType
import com.example.deezermusicapplication.navigation.Navigator
import com.example.deezermusicapplication.data.network.api.DeezerApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class AlbumDetailViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val deezerApi = DeezerApi.instance
    private val albumId: String = savedStateHandle["album_id"] ?: ""

    val isLoading = MutableStateFlow(false)
    val albumDetails = MutableStateFlow(AlbumDetailModel())


    init {
        getAlbumDetails()
    }

    private fun getAlbumDetails() {
        viewModelScope.launch {
            isLoading.update { true }
            try {
                val detailResponse = deezerApi.getAlbumDetails(albumId = albumId)
                Log.i("TrackDetailViewModel", detailResponse.toString())

                val releaseYear = detailResponse.releaseDate.split("-").firstOrNull() ?: "-"
                val mappedAlbumDetail =
                    AlbumDetailModel(
                        albumId = detailResponse.id,
                        albumName = detailResponse.title,
                        albumCover = detailResponse.coverBig,
                        releaseYear = releaseYear,
                        contributors = detailResponse.contributors.map {
                            ContributorModel(
                                name = it.name,
                                role = it.role,
                                type = it.type,
                                photo = it.picture,
                                linkToProfile = it.link
                            )
                        },
                        tracks = detailResponse.tracks.data.map { track->
                            SearchResultItem(
                                id = track.id,
                                name = track.title,
                                artist = track.artist.name,
                                photo = detailResponse.cover,
                                type = SearchType.TRACK,
                            )
                        }
                    )
                albumDetails.update { mappedAlbumDetail }
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

    fun goToTrackDetails(selectedTrack: SearchResultItem) {
        viewModelScope.launch {
            Navigator.navigate(Navigator.NavigationCommand(route = "trackDetailFragment/${selectedTrack.id}"))
        }
    }

}