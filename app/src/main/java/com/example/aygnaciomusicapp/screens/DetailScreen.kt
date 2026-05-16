package com.example.aygnaciomusicapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.aygnaciomusicapp.model.AlbumDetailDto
import com.example.aygnaciomusicapp.services.RetrofitClient
import com.example.aygnaciomusicapp.ui.theme.DarkPlayerBg
import com.example.aygnaciomusicapp.ui.theme.PurpleBackground
import com.example.aygnaciomusicapp.ui.theme.TextDark

@Composable
fun DetailScreen(albumId: String, onBackClick: () -> Unit) {
    var detail by remember { mutableStateOf<AlbumDetailDto?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(albumId) {
        try {
            isLoading = true
            detail = RetrofitClient.apiService.getAlbumDetail(albumId)
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
            detail?.let { album ->
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 88.dp)
                ) {
                    item {
                        Box(modifier = Modifier.fillMaxWidth().height(360.dp)) {
                            AsyncImage(
                                model = album.coverUrl ?: "",
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)),
                                contentScale = ContentScale.Crop
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth().padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                IconButton(
                                    onClick = onBackClick,
                                    modifier = Modifier.background(Color.Black.copy(alpha = 0.3f), RoundedCornerShape(50))
                                ) {
                                    Icon(Icons.Default.ArrowBack, contentDescription = null, tint = Color.White)
                                }
                                IconButton(
                                    onClick = { },
                                    modifier = Modifier.background(Color.Black.copy(alpha = 0.3f), RoundedCornerShape(50))
                                ) {
                                    Icon(Icons.Default.FavoriteBorder, contentDescription = null, tint = Color.White)
                                }
                            }

                            Column(
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(24.dp)
                            ) {
                                Text(
                                    text = album.title ?: "Album Title",
                                    color = Color.White,
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = album.artist ?: "Unknown Artist",
                                    color = Color.White.copy(alpha = 0.8f),
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Spacer(modifier = Modifier.height(16.dp))

                                Surface(
                                    shape = RoundedCornerShape(50),
                                    color = Color.White,
                                    modifier = Modifier.size(48.dp)
                                ) {
                                    IconButton(onClick = { }) {
                                        Icon(
                                            Icons.Default.PlayArrow,
                                            contentDescription = null,
                                            tint = DarkPlayerBg,
                                            modifier = Modifier.size(32.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            shape = RoundedCornerShape(24.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Column(modifier = Modifier.padding(20.dp)) {
                                Text("About this album", color = TextDark, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = album.description ?: "No description available for this classic album.",
                                    style = MaterialTheme.typography.bodyMedium,
                                    lineHeight = 20.sp
                                )
                            }
                        }
                    }

                    item {
                        Card(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .wrapContentWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Artist: ",
                                    fontWeight = FontWeight.Bold,
                                    color = TextDark
                                )
                                Text(
                                    text = album.artist ?: "Unknown",
                                    color = TextDark
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    val currentAlbumTitle = album.title ?: "Song Title"
                    val tracksToShow = if (album.tracks.isEmpty()) List(10) { currentAlbumTitle } else album.tracks.map { it.title }

                    itemsIndexed(tracksToShow) { index, trackTitle ->
                        Card(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp).padding(vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                AsyncImage(
                                    model = album.coverUrl ?: "",
                                    contentDescription = "Mini portada",
                                    modifier = Modifier
                                        .size(44.dp)
                                        .clip(RoundedCornerShape(12.dp)),
                                    contentScale = ContentScale.Crop
                                )

                                Spacer(modifier = Modifier.width(12.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "$trackTitle • Track ${index + 1}",
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = album.artist ?: "Artist",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color.Gray
                                    )
                                }
                                Icon(Icons.Default.MoreVert, contentDescription = null, tint = Color.Gray)
                            }
                        }
                    }
                }
            }
        }

        MiniPlayer(modifier = Modifier.align(Alignment.BottomCenter))
    }
}