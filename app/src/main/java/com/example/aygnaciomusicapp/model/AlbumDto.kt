package com.example.aygnaciomusicapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AlbumDto(
    @SerialName("id") val id: String,
    @SerialName("title") val title: String,
    @SerialName("artist") val artist: String,
    @SerialName("coverUrl") val coverUrl: String
)

@Serializable
data class AlbumDetailDto(
    @SerialName("id") val id: String,
    @SerialName("title") val title: String,
    @SerialName("artist") val artist: String,
    @SerialName("coverUrl") val coverUrl: String,
    @SerialName("description") val description: String,
    @SerialName("tracks") val tracks: List<TrackDto>
)

@Serializable
data class TrackDto(
    @SerialName("id") val id: String,
    @SerialName("title") val title: String
)