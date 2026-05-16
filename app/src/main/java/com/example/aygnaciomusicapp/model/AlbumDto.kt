package com.example.aygnaciomusicapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AlbumDto(
    @SerialName("id") val id: String,
    @SerialName("title") val title: String,
    @SerialName("artist") val artist: String,
    @SerialName("image") val coverUrl: String
)

@Serializable
data class AlbumDetailDto(
    @SerialName("_id") val id: String? = null,
    @SerialName("title") val title: String? = null,
    @SerialName("artist") val artist: String? = null,
    @SerialName("image") val coverUrl: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("tracks") val tracks: List<TrackDto> = emptyList()
)

@Serializable
data class TrackDto(
    @SerialName("id") val id: String,
    @SerialName("title") val title: String
)