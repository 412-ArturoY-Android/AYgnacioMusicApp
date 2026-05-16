package com.example.aygnaciomusicapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.aygnaciomusicapp.model.AlbumDto
import com.example.aygnaciomusicapp.services.RetrofitClient
import com.example.aygnaciomusicapp.ui.theme.PurpleBackground
import com.example.aygnaciomusicapp.ui.theme.PurpleHeader
import com.example.aygnaciomusicapp.ui.theme.TextDark

@Composable
fun HomeScreen(onAlbumClick: (String) -> Unit) {
    var albums by remember { mutableStateOf<List<AlbumDto>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        try {
            albums = RetrofitClient.apiService.getAlbums()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            isLoading = false
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(PurpleBackground)) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 88.dp)
            ) {
                item { HeaderSection(username = "Juan Frausto") }

                item {
                    Text("Albums", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleLarge, color = TextDark, fontWeight = FontWeight.Bold)
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(albums) { album ->
                            AlbumCard(album = album, onClick = { onAlbumClick(album.id) })
                        }
                    }
                }

                item {
                    Text("Recently Played", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleLarge, color = TextDark, fontWeight = FontWeight.Bold)
                }

                items(albums) { album ->
                    RecentTrackRow(album = album)
                }
            }
        }

        MiniPlayer(modifier = Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
fun HeaderSection(username: String) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = PurpleHeader)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Icon(Icons.Default.Menu, contentDescription = null, tint = Color.White)
                Icon(Icons.Default.Search, contentDescription = null, tint = Color.White)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text("Good Morning!", color = Color.White.copy(alpha = 0.7f), style = MaterialTheme.typography.titleMedium)
            Text(username, color = Color.White, style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun AlbumCard(album: AlbumDto, onClick: () -> Unit) {
    Card(
        modifier = Modifier.size(160.dp).clickable { onClick() },
        shape = RoundedCornerShape(24.dp)
    ) {
        Box {
            AsyncImage(
                model = album.coverUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter).background(Color.Black.copy(alpha = 0.6f)).padding(8.dp)) {
                Text(album.title, color = Color.White, maxLines = 1)
            }
        }
    }
}

@Composable
fun RecentTrackRow(album: AlbumDto) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = album.coverUrl,
                contentDescription = null,
                modifier = Modifier.size(56.dp).clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(album.title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
                Text("${album.artist} • Popular Song", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
        }
    }
}