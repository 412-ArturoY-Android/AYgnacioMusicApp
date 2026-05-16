package com.example.aygnaciomusicapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import com.example.aygnaciomusicapp.model.AlbumDetailDto
import com.example.aygnaciomusicapp.services.RetrofitClient
import com.example.aygnaciomusicapp.ui.theme.PurpleBackground
import com.example.aygnaciomusicapp.ui.theme.TextDark

@Composable
fun DetailScreen(albumId: String, onBackClick: () -> Unit) {
    var detail by remember { mutableStateOf<AlbumDetailDto?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(albumId) {
        try {
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
                        Box(modifier = Modifier.fillMaxWidth().height(320.dp)) {
                            AsyncImage(
                                model = album.coverUrl,
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)),
                                contentScale = ContentScale.Crop
                            )
                            IconButton(onClick = onBackClick, modifier = Modifier.padding(16.dp).background(Color.Black.copy(alpha = 0.4f), RoundedCornerShape(50))) {
                                Icon(Icons.Default.ArrowBack, contentDescription = "Atrás", tint = Color.White)
                            }
                        }
                    }

                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            shape = RoundedCornerShape(24.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("About this album", style = MaterialTheme.typography.titleMedium, color = TextDark, fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(album.description, style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                    }

                    items(album.tracks) { track ->
                        Card(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                                Column {
                                    Text(track.title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
                                    Text(album.artist, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                                }
                            }
                        }
                    }
                }
            }
        }

        MiniPlayer(modifier = Modifier.align(Alignment.BottomCenter))
    }
}