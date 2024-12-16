package com.example.deezermusicapplication.presentation.screens.playlist_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.example.deezermusicapplication.R
import com.example.deezermusicapplication.data.local.database.entity.PlaylistEntity
import com.example.deezermusicapplication.utils.extension.defaultScreenPadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistScreen(
    playlists: List<PlaylistEntity>,
    onAddPlaylist: (name: String, description: String, link: String) -> Unit,
    onDeletePlaylist: (id: Int) -> Unit,
    onPlaylistClicked: (id: Int) -> Unit,
    onBackPressed: () -> Unit
) {
    var isDialogVisible by rememberSaveable { mutableStateOf(false) }

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .background(color = MaterialTheme.colorScheme.background)
                .padding(horizontal = 24.dp)
                .padding(top = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "Playlists",
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            LazyColumn {
//                item {
//                    Column {
//                        PlaylistSearchItem(
//                            modifier = Modifier
//                                .clickable {
//                                    onPlaylistClicked(-1)
//                                }
//                                .padding(vertical = 12.dp),
//                            playlistName = "Liked songs",
//                            description = "All your liked songs in one place!",
//                            photo = ""
//                        )
//                        HorizontalDivider()
//                    }
//                }
                items(items = playlists, key = { it.playlistName }) { playlist->
                    Column (
                        modifier = Modifier.animateItem()
                    ) {
                        SwipeToDismissBox(
                            state = rememberSwipeToDismissBoxState(
                                confirmValueChange = {
                                    if (it == SwipeToDismissBoxValue.EndToStart) {
                                        onDeletePlaylist(playlist.playlistId)
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
                                PlaylistSearchItem(
                                    modifier = Modifier
                                        .clickable {
                                            onPlaylistClicked(playlist.playlistId)
                                        }
                                        .padding(vertical = 12.dp),
                                    playlistName = playlist.playlistName,
                                    description = playlist.playlistDescription,
                                    photo = playlist.playlistIconLink
                                )
                            },
                            enableDismissFromStartToEnd = false
                        )
                        HorizontalDivider()
                    }
                }
            }

        }

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .defaultScreenPadding(),
            onClick = {
                isDialogVisible = true
            }
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "add playlist")
        }
    }

    AddPlaylistDialog(
        isVisible = isDialogVisible,
        onAddPlaylist = { name, desc, link ->
            isDialogVisible = false
            onAddPlaylist(name, desc, link)
        },
        onDismissRequest = {
            isDialogVisible = false
        }
    )

}

@Composable
fun AddPlaylistDialog(
    isVisible: Boolean,
    onAddPlaylist: (name: String, description: String, link: String) -> Unit,
    onDismissRequest: () -> Unit
) {
    if (isVisible)
        Dialog(
            onDismissRequest = onDismissRequest
        ) {
            var playlistName: String by rememberSaveable { mutableStateOf("") }
            var playlistDescription: String by rememberSaveable { mutableStateOf("") }
            var playlistImageLink: String by rememberSaveable { mutableStateOf("") }


            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(
                    modifier = Modifier
                        .background(
                            color = Color.White, shape = RoundedCornerShape(12.dp)
                        )
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .size(120.dp)
                            .background(color = Color.LightGray)
                            .padding(16.dp),
                        contentScale = ContentScale.Crop,
                        model = playlistImageLink.takeIf { it.isNotEmpty() }
                            ?: R.drawable.ic_playlist,
                        contentDescription = "cover_photo"
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    TextField(
                        modifier = Modifier.fillMaxWidth(0.8f),
                        value = playlistName,
                        onValueChange = {
                            playlistName = it
                        },
                        placeholder = {
                            Text("Playlist name*")
                        },
                        singleLine = true,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        modifier = Modifier.fillMaxWidth(0.9f),
                        value = playlistDescription,
                        onValueChange = {
                            if (it.length <= 100) playlistDescription = it
                        },
                        placeholder = {
                            Text("Playlist description")
                        },
                        maxLines = 2
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        modifier = Modifier.fillMaxWidth(1f),
                        value = playlistImageLink,
                        onValueChange = {
                            playlistImageLink = it
                        },
                        placeholder = {
                            Text("Link to playlist icon")
                        },
                        singleLine = true,
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(onClick = {
                        onAddPlaylist(playlistName, playlistDescription, playlistImageLink)
                    }) {
                        Text("Add playlist")
                    }
                }
            }
        }
}

@Composable
fun PlaylistSearchItem(
    modifier: Modifier,
    playlistName: String,
    description: String,
    photo: String,
    background: Color = MaterialTheme.colorScheme.surface
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = background)
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .size(80.dp)
                .background(color = Color.LightGray)
                .padding(16.dp),
            contentScale = ContentScale.Crop,
            model = photo.takeIf { it.isNotEmpty() } ?: R.drawable.ic_playlist,
            contentDescription = "cover_photo"
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier
                .height(64.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = playlistName,
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium
            )
            if (description.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlaylistScreenPrev() {
    PlaylistScreen(
        onBackPressed = {},
        playlists = (0..10).map {
            PlaylistEntity(
                playlistName = "Liked songs",
                playlistDescription = "Default playlist",
                playlistIconLink = ""
            )
        }, onAddPlaylist = { s: String, s1: String, s2: String -> }, onDeletePlaylist = {},
        onPlaylistClicked = {  }
    )
}