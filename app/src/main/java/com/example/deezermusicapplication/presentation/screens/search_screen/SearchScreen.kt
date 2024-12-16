package com.example.deezermusicapplication.presentation.screens.search_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.deezermusicapplication.R
import com.example.deezermusicapplication.data.local.database.entity.PlaylistEntity
import com.example.deezermusicapplication.data_models.SearchType
import com.example.deezermusicapplication.data_models.SearchResultItem
import com.example.deezermusicapplication.presentation.screens.playlist_screen.PlaylistSearchItem
import com.example.deezermusicapplication.utils.extension.defaultScreenPadding


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    isLoading: Boolean,
    searchValue: String,
    onSearchValueChanged: (String) -> Unit,
    selectedSearchType: SearchType,
    onSearchFilterChanged: (SearchType) -> Unit,
    searchResult: List<SearchResultItem>,
    onSearchResultClicked: (SearchResultItem) -> Unit,
    onShowPlaylistDialog: (SearchResultItem) -> Unit,
    onTogglePlaylist: (playlistId: Int) -> Unit,
    onHidePlaylistDialog: () -> Unit,
    isPlaylistDialogVisible: Boolean,
    allPlaylists: List<PlaylistEntity>,
    addedPlaylists: List<PlaylistEntity>,
    onGoToPlaylist: () -> Unit,
    onSearch: () -> Unit
) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .defaultScreenPadding()
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically
        ) {
            SearchField(
                modifier = Modifier.weight(1f),
                value = searchValue,
                onValueChanged = onSearchValueChanged,
                onSearch = onSearch
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Choose category",
            style = TextStyle(
                fontSize = 18.sp
            )
        )
        Row (
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SearchType.entries.forEach { filter->
                FilterChip(
                    selected = filter == selectedSearchType,
                    label = {
                        Text(filter.displayName)
                    },
                    onClick = {
                        onSearchFilterChanged(filter)
                    }
                )
            }
        }

        if (!isLoading) {
            if (searchResult.isEmpty()) {
                Column (
                    modifier = Modifier.fillMaxSize()
                ) {
                    Spacer(modifier = Modifier.fillMaxHeight(0.3f))
                    Text(
                        text = "Not seeing any result?",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        text = "Start searching for your favorite track or the album, now!",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Light
                    )
                }
            }
            else when (selectedSearchType) {
                SearchType.TRACK -> {
                    // FIXME - USE RECYCLERVIEW AS DEMO, INSTEAD
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(items = searchResult) { track ->
                            TrackSearchItem(
                                songName = track.name,
                                artist = track.artist,
                                photo = track.photo,
                                onClick = { onSearchResultClicked(track) },
                                onLongClick = { onShowPlaylistDialog(track) }
                            )
                        }
                    }
                }
                SearchType.ALBUM -> {
                    LazyVerticalGrid(
                        modifier = Modifier.fillMaxWidth(),
                        columns = GridCells.Adaptive(minSize = 100.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(items = searchResult) { album->
                            AlbumSearchItem(
                                albumName = album.name,
                                artist = album.artist,
                                photo = album.photo,
                                onClick = { onSearchResultClicked(album) }
                            )
                        }
                    }
                }
            }
        }
    }

    if (isPlaylistDialogVisible) {
        ModalBottomSheet(
            containerColor = MaterialTheme.colorScheme.surface,
            dragHandle = {},
            onDismissRequest = {
                onHidePlaylistDialog()
            }
        ) {
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .defaultScreenPadding()
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Select a playlist",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(
                    modifier = Modifier.padding(vertical = 12.dp)
                )
                LazyColumn {
                    itemsIndexed (items = allPlaylists) { index, playlist->
                        Row (
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column (
                                modifier = Modifier.weight(1f)
                            ) {
                                PlaylistSearchItem(
                                    modifier = Modifier
                                        .clickable {
                                            onTogglePlaylist(playlist.playlistId)
                                        }
                                        .padding(vertical = 12.dp),
                                    playlistName = playlist.playlistName,
                                    description = playlist.playlistDescription,
                                    photo = playlist.playlistIconLink,
                                    background = MaterialTheme.colorScheme.surface
                                )
                            }
                            Checkbox(
                                checked = playlist.playlistId in addedPlaylists.map { it.playlistId },
                                onCheckedChange = {
                                    onTogglePlaylist(playlist.playlistId)
                                }
                            )
                        }
                        if (index != allPlaylists.lastIndex)
                            HorizontalDivider()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TrackSearchItem(
    modifier: Modifier = Modifier,
    songName: String,
    artist: String,
    photo: Any?,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    Row (
        modifier = modifier
            .background(color = Color.White)
            .combinedClickable (
                onClick = onClick,
                onLongClick = onLongClick
            )
    ) {
        AsyncImage(
            modifier = Modifier.size(64.dp)
                // Placeholder
                .background(color = Color.Gray),
            contentScale = ContentScale.Crop,
            model = photo,
            contentDescription = "cover_photo"
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column (
            modifier = Modifier.height(64.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(songName)
            Spacer(modifier = Modifier.height(4.dp))
            Text(artist)
        }
    }
}

@Composable
fun AlbumSearchItem(
    albumName: String,
    artist: String,
    photo: Any?,
    onClick: () -> Unit
) {
    Column {
        AsyncImage(
            modifier = Modifier
                .size(120.dp)
                // Placeholder
                .background(color = Color.Gray)
                .clickable {
                    onClick()
                },
            contentScale = ContentScale.Crop,
            model = photo,
            contentDescription = "cover_photo"
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column (
            modifier = Modifier
                .width(120.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(albumName)
            Spacer(modifier = Modifier.height(4.dp))
            Text("by $artist")
        }
    }
}


@Composable
fun SearchField(
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle(
        fontSize = 18.sp
    ),
    value: String,
    onValueChanged: (String) -> Unit,
    onSearch: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    TextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChanged,
        shape = RoundedCornerShape(12.dp),
        placeholder = {
            Text(
                text = "Search",
                style = textStyle,
                // FIXME - THEME
                color = Color.Black.copy(0.3f)
            )
        },
        textStyle = textStyle,
        leadingIcon = {
            Icon(
                // FIXME - Better search icon
                imageVector = Icons.Default.Search,
                contentDescription = "search"
            )
        },
        // removed indicator, by making it transparent
        colors = TextFieldDefaults.colors(
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(onSearch = {
            // hide keyboard on ime action
            focusManager.clearFocus()
            onSearch()
        }),
        singleLine = true
    )
}

@Preview (showBackground = true)
@Composable
private fun SearchScreenPrev() {
    SearchScreen(
        isLoading = false,
        searchValue = "",
        onSearchValueChanged = {},
        selectedSearchType = SearchType.TRACK,
        onSearchFilterChanged = {},
        searchResult = listOf(),
        onSearchResultClicked = {},
        onGoToPlaylist = {},
        onSearch = {},
        onShowPlaylistDialog = {},
        isPlaylistDialogVisible = false,
        onTogglePlaylist = {},
        addedPlaylists = emptyList(),
        allPlaylists = emptyList(),
        onHidePlaylistDialog = {}
    )
}