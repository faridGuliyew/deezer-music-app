package com.example.deezermusicapplication.presentation.screens.album_detail_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.setPadding
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.compose.AsyncImage
import com.example.deezermusicapplication.data_models.AlbumDetailModel
import com.example.deezermusicapplication.data_models.ContributorModel
import com.example.deezermusicapplication.data_models.SearchResultItem
import com.example.deezermusicapplication.data_models.TrackDetailModel
import com.example.deezermusicapplication.presentation.screens.search_screen.TrackSearchItem
import com.example.deezermusicapplication.utils.extension.defaultScreenPadding


@Composable
fun AlbumDetailScreen(
    albumDetails: AlbumDetailModel,
    onBackPressed: () -> Unit,
    onGoToTrack : (track: SearchResultItem) -> Unit
) {
    Scaffold(
        containerColor = Color.White,
        topBar = {
            IconButton(
                modifier = Modifier
                    .padding(top = 24.dp)
                    .padding(horizontal = 16.dp),
                onClick = {
                    onBackPressed()
                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = "go back"
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Gray),
                model = albumDetails.albumCover,
                contentDescription = "album cover",
                contentScale = ContentScale.FillWidth
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = albumDetails.albumName,
                    style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = albumDetails.releaseYear,
                    style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium)
                )
            }
            Spacer(modifier = Modifier.height(36.dp))
            Text(
                text = "Artists",
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium)
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow (
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(items = albumDetails.contributors) { contributor->
                    ContributorItem(
                        contributor = contributor
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Songs",
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium)
            )
            Spacer(modifier = Modifier.height(8.dp))
            AlbumTracksRecyclerView(
                tracks = albumDetails.tracks,
                onClick = onGoToTrack
            )
        }
    }
}


@Composable
fun AlbumTracksRecyclerView(
    tracks: List<SearchResultItem>,
    onClick: (SearchResultItem) -> Unit
) {
    AndroidView(
        factory = { context ->
            // Created the RecyclerView programmatically for demo
            RecyclerView(context).apply {
                layoutManager = LinearLayoutManager(context)
            }
        },
        update = {
            it.adapter = AlbumTracksAdapter(
                tracks = tracks,
                onClick = onClick,
                onLongClick = { }
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
    )
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
private fun AlbumDetailScreenPrev() {
    AlbumDetailScreen(albumDetails = AlbumDetailModel(
        albumId = "homero",
        albumName = "Jeannie Dodson",
        albumCover = "prompta",
        releaseYear = "potenti",
        contributors = listOf(),
        tracks = listOf()
    ), onBackPressed = {}, onGoToTrack = {}

    )
}