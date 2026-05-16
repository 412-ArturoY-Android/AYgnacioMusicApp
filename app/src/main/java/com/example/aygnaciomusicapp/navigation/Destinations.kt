package com.example.aygnaciomusicapp.navigation

import kotlinx.serialization.Serializable

@Serializable
object HomeDestination

@Serializable
data class DetailDestination(val albumId: String)