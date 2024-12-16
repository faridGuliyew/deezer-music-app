package com.example.deezermusicapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.deezermusicapplication.data.local.database.AppDatabase
import com.example.deezermusicapplication.navigation.Navigator
import com.example.deezermusicapplication.presentation.screens.add_question_screen.addQuestionFragment
import com.example.deezermusicapplication.presentation.screens.album_detail_screen.albumDetailFragment
import com.example.deezermusicapplication.presentation.screens.home_screen.HomeScreen
import com.example.deezermusicapplication.presentation.screens.playlist_detail_screen.playlistDetailFragment
import com.example.deezermusicapplication.presentation.screens.playlist_screen.playlistFragment
import com.example.deezermusicapplication.presentation.screens.quiz_listing_screen.quizListingFragment
import com.example.deezermusicapplication.presentation.screens.quiz_screen.quizFragment
import com.example.deezermusicapplication.presentation.screens.search_screen.searchFragment
import com.example.deezermusicapplication.presentation.screens.track_detail_screen.trackDetailFragment

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        AppDatabase.init(context = this)
        setContent {
            HomeScreen(scope = lifecycleScope)
        }
    }
}