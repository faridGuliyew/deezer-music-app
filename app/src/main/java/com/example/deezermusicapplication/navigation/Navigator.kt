package com.example.deezermusicapplication.navigation

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import com.example.deezermusicapplication.navigation.Navigator.NavigationCommand
import com.example.deezermusicapplication.navigation.Navigator.navigationFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

object Navigator {
    val navigationFlow = MutableSharedFlow<NavigationCommand?>()

    fun handleNavigation(
        navController: NavController,
        scope: CoroutineScope
    ) {
        scope.launch {
            navigationFlow
                .collectLatest {
                it?.let {
                    println("Navigator - navigation command: $it")
                    it.options(navController)
                    if (it.route == "back") {
                        navController.popBackStack()
                    } else {
                        navController.navigate(
                            route = it.route,
                            builder = it.builder
                        )
                    }
                }
            }
        }
    }

    suspend fun navigate(command: NavigationCommand) {
        navigationFlow.emit(command)
    }

    data class NavigationCommand (
        val route: String,
        val options: NavController.() -> Unit = {},
        val builder: NavOptionsBuilder.() -> Unit = {}
    )
}