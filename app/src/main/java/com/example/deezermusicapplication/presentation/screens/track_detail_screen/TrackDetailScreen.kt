package com.example.deezermusicapplication.presentation.screens.track_detail_screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SliderState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.deezermusicapplication.data.local.database.entity.PlaylistEntity
import com.example.deezermusicapplication.data_models.ContributorModel
import com.example.deezermusicapplication.data_models.TrackDetailModel
import com.example.deezermusicapplication.presentation.screens.playlist_screen.PlaylistSearchItem
import com.example.deezermusicapplication.utils.extension.defaultScreenPadding


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackDetailScreen(
    trackDetails: TrackDetailModel,
    allPlaylists: List<PlaylistEntity>,
    addedPlaylists: List<PlaylistEntity>,
    isMusicPlaying: Boolean,
    playTime : Int,
    onBackPressed: () -> Unit,
    onTogglePlaylist: (playlistId: Int) -> Unit,
    onGoToAlbum: () -> Unit,
    onStartMusic : () -> Unit,
    onPauseMusic : () -> Unit,
    onSeekMusic: (value: Float) -> Unit
) {
    val mainColor by animateColorAsState(
        targetValue = trackDetails.backgroundColor,
        animationSpec = tween(durationMillis = 1000)
    )
    var isAddToPlaylistSheetVisible by rememberSaveable { mutableStateOf(false) }
    Scaffold(
        containerColor = mainColor,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .systemBarsPadding()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = {
                        onBackPressed()
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "go back"
                    )
                }

                IconButton(
                    onClick = {
                        isAddToPlaylistSheetVisible = true
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "add to favorites"
                    )
                }
            }
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .defaultScreenPadding()
                    .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

               /* val sliderState = remember { SliderState(valueRange = 0f..30f) }
                Column {
                    Slider(
                        state = sliderState,
                        colors = SliderDefaults.colors(
                            thumbColor = Color.Black
                        )
                    )
                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Format play time
                        Text("0:${if (playTime.toString().length > 1) playTime else "0$playTime"}")
                        Text("-0:${if ((30 - playTime).toString().length > 1) (30 - playTime) else "0${(30 - playTime)}"}")
                    }
                }*/
                IconButton(
                    modifier = Modifier.border(
                        width = 1.dp,
                        color = Color.Black,
                        shape = CircleShape
                    ),
                    onClick = {
                        if (isMusicPlaying) onPauseMusic()
                        else onStartMusic()
                    }
                ) {
                    Icon(
                        imageVector = if (isMusicPlaying) Icons.Default.Clear else Icons.Default.PlayArrow,
                        contentDescription = "play preview",
                        tint = Color.Black
                    )
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 24.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .background(color = Color.Gray),
                model = trackDetails.albumCover,
                contentDescription = "track cover",
                contentScale = ContentScale.FillWidth
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    modifier = Modifier.weight(1f),
                    text = trackDetails.trackName,
                    style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
                )

                Text(
                    text = trackDetails.releaseYear,
                    style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium)
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                modifier = Modifier.clickable {
                    onGoToAlbum()
                },
                text = "Album: ${trackDetails.albumName}",
                style = TextStyle(fontSize = 16.sp, textDecoration = TextDecoration.Underline)
            )
            Spacer(modifier = Modifier.height(36.dp))
            Text(
                text = "Artists",
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium)
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow (
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(items = trackDetails.contributors) { contributor->
                    ContributorItem(
                        contributor = contributor
                    )
                }
            }
        }
    }


    if (isAddToPlaylistSheetVisible) {
        ModalBottomSheet(
            containerColor = MaterialTheme.colorScheme.surface,
            dragHandle = {},
            onDismissRequest = {
            isAddToPlaylistSheetVisible = false
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


@Composable
fun ContributorItem(
    contributor: ContributorModel
) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier
                .clip(CircleShape)
                .size(64.dp)
                .background(color = Color.Gray),
            model = contributor.photo,
            contentDescription = "track cover"
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = contributor.name,
            style = TextStyle(
                fontWeight = FontWeight.Medium
            )
        )
        Text(text = "${contributor.role} ${contributor.type}")
    }
}
@Preview
@Composable
private fun TrackDetailScreenPrev() {
    TrackDetailScreen(trackDetails = TrackDetailModel(
        trackName = "Dominick Rosa",
        trackPreview = "dicit",
        albumCover = "instructior",
        releaseYear = "eruditi",
        albumName = "Madge Horn",
        albumId = "sea", trackId = "donec", backgroundColor =Color.White, contributors = listOf(),
    ),
        onBackPressed = {},
        onGoToAlbum = {},
        onStartMusic = {},
        onPauseMusic = {},
        onSeekMusic = {},
        playTime = 0,
        isMusicPlaying = true,
        allPlaylists = emptyList(),
        addedPlaylists = emptyList(),
        onTogglePlaylist = {}

    )
}