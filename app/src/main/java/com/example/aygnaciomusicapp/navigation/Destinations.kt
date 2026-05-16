package com.example.aygnaciomusicapp.navigation

import kotlinx.serialization.Serializable

@Serializable
object HomeDestination

@Serializable
data class DetailDestination(val albumId: String)

@Serializable
data class Album(
    val id: String,
    val title: String,
    val artist: String,
    val coverUrl: String, // Cambiar según la clave real de tu JSON
    val type: String? = null
)

@Serializable
data class AlbumDetail(
    val id: String,
    val title: String,
    val artist: String,
    val coverUrl: String,
    val description: String,
    val tracks: List<Track>
)

@Serializable
data class Track(
    val id: String,
    val title: String,
    val duration: String? = null
)