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
import com.example.aygnaciomusicapp.ui.theme.TextDark
import androidx.compose.ui.graphics.Brush
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.MoreVert

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
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Albums",
                            style = MaterialTheme.typography.titleLarge,
                            color = TextDark,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "See more",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF8A56FF),
                            modifier = Modifier.clickable {  }
                        )
                    }

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
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Recently Played",
                            style = MaterialTheme.typography.titleLarge,
                            color = TextDark,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "See more",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF8A56FF),
                            modifier = Modifier.clickable { /* Acción opcional */ }
                        )
                    }
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
    val gradientColors = listOf(
        Color(0xFF8A56FF),
        Color(0xFF4F75FF)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column(
            modifier = Modifier
                .background(brush = Brush.verticalGradient(colors = gradientColors))
                .padding(24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(Icons.Default.Menu, contentDescription = null, tint = Color.White)
                Icon(Icons.Default.Search, contentDescription = null, tint = Color.White)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Good Morning!",
                color = Color.White.copy(alpha = 0.7f),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = username,
                color = Color.White,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun AlbumCard(album: AlbumDto, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .size(width = 180.dp, height = 180.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(24.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = album.coverUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(Color.Black.copy(alpha = 0.4f))
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f).padding(end = 4.dp)) {
                        Text(
                            text = album.title,
                            color = Color.White,
                            maxLines = 1,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = album.artist,
                            color = Color.White.copy(alpha = 0.7f),
                            maxLines = 1,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(50),
                        color = Color.White,
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Play",
                            tint = Color(0xFF1F0B42),
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RecentTrackRow(album: AlbumDto) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = album.coverUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = album.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
                Text(
                    text = "${album.artist} • Popular Song",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    maxLines = 1
                )
            }

            IconButton(onClick = {  }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Opciones",
                    tint = Color.Gray
                )
            }
        }
    }
}