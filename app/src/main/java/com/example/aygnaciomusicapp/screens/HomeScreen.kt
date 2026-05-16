package com.example.aygnaciomusicapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.aygnaciomusicapp.navigation.Album
import com.example.aygnaciomusicapp.services.RetrofitClient

@Composable
fun HomeScreen(onAlbumClick: (String) -> Unit) {
    // Estado para almacenar la lista de álbumes (Idealmente esto va en un ViewModel)
    var albums by remember { mutableStateOf<List<Album>>(emptyList()) }

    LaunchedEffect(Unit) {
        try {
            albums = RetrofitClient.apiService.getAlbums()
        } catch (e: Exception) {
            // Manejar error
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(Color(#F0E6FF)) { // Fondo lila claro
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 80.dp) // Espacio para el mini reproductor
        ) {
            // 1. Header Card (Bienvenida)
            item {
                HeaderSection(username = "Juan Frausto")
            }

            // 2. Sección de Álbumes (Horizontal)
            item {
                SectionTitle(title = "Albums")
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(albums) { album ->
                        AlbumCard(album = album, onClick = { onAlbumClick(album.id) })
                    }
                }
            }

            // 3. Sección Recientes (Vertical)
            item {
                SectionTitle(title = "Recently Played")
            }

            items(albums) { album -> // Usando los mismos datos como ejemplo para la lista vertical
                RecentTrackRow(album = album)
            }
        }

        // Mini Player Flotante (Abajo del todo)
        MiniPlayer(modifier = Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
fun SectionTitle(title: String) {
    TODO("Not yet implemented")
}

@Composable
fun HeaderSection(username: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Color(#8A56FF)) // Morado vivo
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text("Good Morning!", color = Color.White.copy(alpha = 0.7f), style = MaterialTheme.typography.titleMedium)
            Text(username, color = Color.White, style = MaterialTheme.typography.headlineLarge)
        }
    }
}

@Composable
fun AlbumCard(album: Album, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .size(160.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(24.dp)
    ) {
        Box {
            AsyncImage(
                model = album.coverUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            // Overlay inferior con el título
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(Color.Black.copy(alpha = 0.6f))
                    .padding(8.dp)
            ) {
                Text(album.title, color = Color.White, maxLines = 1)
            }
        }
    }
}

@Composable
fun RecentTrackRow(album: Album) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = album.coverUrl,
                contentDescription = null,
                modifier = Modifier.size(56.dp).clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1fr)) {
                Text(album.title, style = MaterialTheme.typography.bodyLarge)
                Text("${album.artist} • Popular Song", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
        }
    }
}