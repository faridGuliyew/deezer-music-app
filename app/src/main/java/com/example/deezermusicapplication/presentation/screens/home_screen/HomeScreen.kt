package com.example.deezermusicapplication.presentation.screens.home_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.deezermusicapplication.R
import com.example.deezermusicapplication.navigation.Navigator
import com.example.deezermusicapplication.presentation.screens.add_question_screen.addQuestionFragment
import com.example.deezermusicapplication.presentation.screens.album_detail_screen.albumDetailFragment
import com.example.deezermusicapplication.presentation.screens.playlist_detail_screen.playlistDetailFragment
import com.example.deezermusicapplication.presentation.screens.playlist_screen.playlistFragment
import com.example.deezermusicapplication.presentation.screens.quiz_listing_screen.quizListingFragment
import com.example.deezermusicapplication.presentation.screens.quiz_screen.quizFragment
import com.example.deezermusicapplication.presentation.screens.search_screen.searchFragment
import com.example.deezermusicapplication.presentation.screens.track_detail_screen.trackDetailFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    scope: CoroutineScope
) {

    Column(modifier = Modifier.fillMaxSize()) {
        val navController = rememberNavController()
        val focusManager = LocalFocusManager.current

        LaunchedEffect(Unit) {
            Navigator.handleNavigation(
                navController = navController,
                scope = scope
            )
        }
        NavHost(
            modifier = Modifier.weight(1f)
                .clickable(interactionSource = null, indication = null) {
                    // Hide keyboard when pressed outside, somewhere
                    focusManager.clearFocus()
                },
            navController = navController,
            startDestination = "searchFragment"
        ) {
            // include all fragments here
            searchFragment()
            trackDetailFragment()
            albumDetailFragment()
            playlistFragment()
            playlistDetailFragment()
            addQuestionFragment()
            quizFragment()
            quizListingFragment()
        }

        BottomAppBar {
            Box(modifier = Modifier.weight(1f)
                .clickable {
                    scope.launch {


                        Navigator.navigate(Navigator.NavigationCommand(
                            route = "searchFragment",
                            builder = {
                                popUpTo(route = "searchFragment") {
                                    inclusive = true
                                }
                            }
                        ))
                    }
                },
                contentAlignment = Alignment.Center) {

                Image(
                    modifier = Modifier
                        .padding(12.dp)
                        .size(32.dp),
                    imageVector = Icons.Default.Home,
                    contentDescription = "playlist"
                )
            }
            Box(modifier = Modifier.weight(1f)
                .clickable {
                    scope.launch {


                        Navigator.navigate(Navigator.NavigationCommand(
                            route = "playlistFragment",
                            builder = {
                                popUpTo(route = "playlistFragment") {
                                    inclusive = true
                                }
                            }
                        ))
                    }
                }, contentAlignment = Alignment.Center) {
                Image(
                    modifier = Modifier
                        .padding(12.dp)
                        .size(32.dp),
                    painter = painterResource(R.drawable.ic_playlist),
                    contentDescription = "playlist"
                )
            }
        }
    }
}

@Preview (showBackground = true)
@Composable
private fun HomeScreenPrev() {
    HomeScreen(
        scope = CoroutineScope(Dispatchers.Main)
    )
}