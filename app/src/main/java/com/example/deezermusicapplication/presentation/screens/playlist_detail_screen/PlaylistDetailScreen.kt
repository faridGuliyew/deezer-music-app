package com.example.deezermusicapplication.presentation.screens.playlist_detail_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.deezermusicapplication.R
import com.example.deezermusicapplication.data.local.database.entity.PlaylistEntity
import com.example.deezermusicapplication.data.local.database.entity.TrackEntity
import com.example.deezermusicapplication.presentation.screens.search_screen.TrackSearchItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistDetailScreen(
    playlist: PlaylistEntity,
    tracks: List<TrackEntity>,
    relatedQuizCount: Int,
    onGoToTrack: (trackId: String) -> Unit,
    onRemoveTrack: (trackId: String) -> Unit,
    onAddToQuiz: (trackId: String) -> Unit,
    onGoToQuizListing: () -> Unit,
    onBackPressed: () -> Unit
) {
    Scaffold(
        containerColor = Color.White,
        topBar = {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    modifier = Modifier,
                    onClick = {
                        onBackPressed()
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "go back"
                    )
                }
                Text(
                    modifier = Modifier.weight(1f),
                    text = playlist.playlistName,
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                )
                IconButton(
                    onClick = {
                        onGoToQuizListing()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "start quiz"
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
                    .fillMaxWidth()
                    .heightIn(max = 200.dp)
                    .background(color = Color.Gray),
                model = playlist.playlistIconLink.takeIf { it.isNotEmpty() } ?: R.drawable.ic_playlist,
                contentDescription = "album cover",
                contentScale = ContentScale.FillWidth
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = playlist.playlistDescription,
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Light)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "${tracks.size} Song(s)",
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium)
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (tracks.isNotEmpty()) {
                LazyColumn (
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(items = tracks, key = { it.trackId }) { track->
                        SwipeToDismissBox(
                            modifier = Modifier.animateItem(),
                            state = rememberSwipeToDismissBoxState(
                                confirmValueChange = {
                                    if (it == SwipeToDismissBoxValue.EndToStart) {
                                        onRemoveTrack(track.trackId)
                                    }
                                    true
                                },
                                positionalThreshold = {
                                    it / 2
                                }
                            ),
                            backgroundContent = {
                                Row(modifier = Modifier.fillMaxSize().background(color = Color.Red)) {}
                            },
                            content = {
                                Row (
                                    modifier = Modifier.background(color = Color.White),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    TrackSearchItem (
                                        modifier = Modifier.weight(1f),
                                        songName = track.name,
                                        artist = track.artist,
                                        photo = track.photo,
                                        onClick = {
                                            onGoToTrack(track.trackId)
                                        },
                                        onLongClick = {}
                                    )
                                    IconButton(onClick = {
                                        onAddToQuiz(track.trackId)
                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.Add,
                                            contentDescription = "add to quiz"
                                        )
                                    }
                                }
                            },
                            enableDismissFromStartToEnd = false
                        )
                    }
                }
            } else {
                Column (
                    modifier = Modifier.fillMaxSize()
                ) {
                    Spacer(modifier = Modifier.fillMaxHeight(0.3f))
                    Text(
                        text = "Not seeing any songs?",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        text = "Start collecting for your favorite tracks in one place!",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Light
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PlaylistDetailScreenPrev() {
    PlaylistDetailScreen(playlist = PlaylistEntity(
        playlistName = "Erin Russo",
        playlistDescription = "scripta",
        playlistIconLink = ""
    ), tracks = listOf(
        TrackEntity(
            trackId = "moderatius",
            name = "Nichole Martin",
            artist = "efficitur",
            preview = "omnesque",
            photo = null

        )
    ), relatedQuizCount = 2, onGoToTrack = {}, onBackPressed = {}, onRemoveTrack = {}, onAddToQuiz = {}, onGoToQuizListing = {}

    )
}