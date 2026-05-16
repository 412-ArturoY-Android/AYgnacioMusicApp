package com.example.aygnaciomusicapp.services

import com.example.aygnaciomusicapp.model.AlbumDetailDto
import com.example.aygnaciomusicapp.model.AlbumDto
import retrofit2.http.GET
import retrofit2.http.Path

interface MusicApiService {
    @GET("api/albums")
    suspend fun getAlbums(): List<AlbumDto>

    @GET("api/albums/{id}")
    suspend fun getAlbumDetail(@Path("id") id: String): AlbumDetailDto
}