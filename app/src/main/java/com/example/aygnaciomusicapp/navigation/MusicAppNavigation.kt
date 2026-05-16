package com.example.aygnaciomusicapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute

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
            // Obtenemos los argumentos de forma segura
            val detailArgs = backStackEntry.toRoute<DetailDestination>()
            DetailScreen(
                albumId = detailArgs.albumId,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}

@Composable
fun HomeScreen(onAlbumClick: (ERROR) -> Unit) {
    TODO("Not yet implemented")
}

@Composable
fun DetailScreen(albumId: String, onBackClick: () -> Boolean) {
    TODO("Not yet implemented")
}