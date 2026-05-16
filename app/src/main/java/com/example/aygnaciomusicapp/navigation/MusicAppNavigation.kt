package com.example.aygnaciomusicapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.aygnaciomusicapp.screens.DetailScreen
import com.example.aygnaciomusicapp.screens.HomeScreen

@Composable
fun MusicAppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = HomeDestination
    ) {
        composable<HomeDestination> {
            HomeScreen(
                onAlbumClick = { albumId ->
                    navController.navigate(DetailDestination(albumId = albumId))
                }
            )
        }
        composable<DetailDestination> { backStackEntry ->
            val args = backStackEntry.toRoute<DetailDestination>()
            DetailScreen(
                albumId = args.albumId,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}