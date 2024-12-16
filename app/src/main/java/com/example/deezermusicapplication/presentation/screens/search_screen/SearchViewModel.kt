package com.example.deezermusicapplication.presentation.screens.search_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deezermusicapplication.data.local.database.AppDatabase
import com.example.deezermusicapplication.data.local.database.entity.PlaylistEntity
import com.example.deezermusicapplication.data.local.database.entity.TrackEntity
import com.example.deezermusicapplication.data_models.SearchType
import com.example.deezermusicapplication.data_models.SearchResultItem
import com.example.deezermusicapplication.navigation.Navigator
import com.example.deezermusicapplication.data.network.api.DeezerApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val deezerApi = DeezerApi.instance

    val isLoading = MutableStateFlow(false)
    val searchValue = MutableStateFlow("")
    val selectedSearchType = MutableStateFlow(SearchType.TRACK)
    val searchResult = MutableStateFlow<List<SearchResultItem>>(emptyList())

    private val tracksDao = AppDatabase.getInstance().getTracksDao()
    private val playlistDao = AppDatabase.getInstance().getPlaylistDao()
    private val playlistTrackRelationDao = AppDatabase.getInstance().getPlaylistTrackRelationDao()
    val allPlaylists = MutableStateFlow<List<PlaylistEntity>>(emptyList())
    val addedPlaylists =  MutableStateFlow<List<PlaylistEntity>>(emptyList())
    var isPlaylistDialogVisible = MutableStateFlow(false)

    private var lastCachedTrackSearch : Pair<String, List<SearchResultItem>> = "" to emptyList()
    private var lastCachedAlbumSearch : Pair<String, List<SearchResultItem>> = "" to emptyList()

    private var currentPlaylistDialogTrack : SearchResultItem? = null

    init {
        getPlaylists()
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

    private fun getAddedPlaylists(trackId: String) {
        viewModelScope.launch (Dispatchers.IO) {
            playlistTrackRelationDao.getPlaylistsForTrack(trackId = trackId).collectLatest { dbPlaylists->
                addedPlaylists.update {
                    dbPlaylists
                }
            }
        }
    }

    fun togglePlaylist(playlistId: Int) {
        viewModelScope.launch (Dispatchers.IO) {
            // Add track to list of all saved tracks, first
            val track = currentPlaylistDialogTrack ?: return@launch
            tracksDao.addTrack(
                trackEntity = TrackEntity(
                    trackId = track.id,
                    name = track.name,
                    artist = track.artist,
                    photo = track.photo.toString(),
                    preview = ""
                )
            )
            // Check if track was already added to the playlist
            if (playlistId in addedPlaylists.value.map { it.playlistId }) {
                // Remove track from playlist
                playlistTrackRelationDao.removeTrackFromPlaylist(
                    trackId = track.id, playlistId = playlistId
                )
            } else {
                // Add track to playlist
                playlistTrackRelationDao.addTrackToPlaylist(
                    trackId = track.id,
                    playlistId = playlistId
                )
            }
        }
    }

    fun onSearchValueChanged(newValue: String) {
        searchValue.update { newValue }
    }
    fun onSearchFilterChanged(newFilter: SearchType) {
        if (newFilter == selectedSearchType.value) return
        selectedSearchType.update { newFilter }
        onSearch() // Make a search request, when filter is changed
    }
    fun onSearchResultClicked(item: SearchResultItem) {
        when (item.type) {
            SearchType.TRACK -> {
                viewModelScope.launch {
                Navigator.navigate(
                    command = Navigator.NavigationCommand(
                        route = "trackDetailFragment/${item.id}"
                    )
                )
                    }
            }

            SearchType.ALBUM -> {
                viewModelScope.launch {
                    Navigator.navigate(
                        command = Navigator.NavigationCommand(
                            route = "albumDetailFragment/${item.id}"
                        )
                    )
                }
            }
        }
    }
    fun onGoToPlaylist() {
        viewModelScope.launch {
            Navigator.navigate(Navigator.NavigationCommand(route = "playlistFragment"))
        }
    }

    fun hidePlaylistDialog() {
        isPlaylistDialogVisible.update { false }
    }

    fun showPlaylistDialog(track: SearchResultItem) {
        viewModelScope.launch {
            getAddedPlaylists(track.id)
            currentPlaylistDialogTrack = track
            isPlaylistDialogVisible.emit(true)
        }
    }

    fun onSearch() {
        val query = searchValue.value
        if (query.isEmpty()) return

        when (selectedSearchType.value) {
            SearchType.TRACK -> {
                // check if already searched recently
                if (query == lastCachedTrackSearch.first) {
                    return searchResult.update { lastCachedTrackSearch.second }
                }
                viewModelScope.launch {
                    isLoading.update { true }
                    try {
                        val trackResponse = deezerApi.searchByTrack(trackName = query)
                        Log.i("SearchViewModel", trackResponse.toString())
                        val mappedResponse = trackResponse.data.map { responseItem->
                            SearchResultItem(
                                id = responseItem.id,
                                type = SearchType.TRACK,
                                name = responseItem.title,
                                artist = responseItem.artist.name,
                                photo = responseItem.album.cover
                            )
                        }
                        searchResult.update { mappedResponse }
                        lastCachedTrackSearch = query to mappedResponse
                    } catch (e:Exception) {
                        e.printStackTrace()
                    } finally {
                        isLoading.update { false }
                    }
                }
            }
            SearchType.ALBUM -> {
                // check if already searched recently
                if (query == lastCachedAlbumSearch.first) {
                    return searchResult.update { lastCachedAlbumSearch.second }
                }
                viewModelScope.launch {
                    isLoading.update { true }
                    try {
                        val albumResponse = deezerApi.searchAlbum(albumName = query)
                        Log.i("SearchViewModel", albumResponse.toString())
                        val mappedResponse = albumResponse.data.map { responseItem->
                            SearchResultItem(
                                id = responseItem.id,
                                type = SearchType.ALBUM,
                                name = responseItem.title,
                                artist = responseItem.artist.name,
                                photo = responseItem.cover
                            )
                        }
                        searchResult.update { mappedResponse }
                        lastCachedAlbumSearch = query to mappedResponse
                    } catch (e:Exception) {
                        e.printStackTrace()
                    } finally {
                        isLoading.update { false }
                    }
                }
            }
        }
    }
}