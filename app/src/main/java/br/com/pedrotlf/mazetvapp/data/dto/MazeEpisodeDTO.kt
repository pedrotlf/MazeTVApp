package br.com.pedrotlf.mazetvapp.data.dto

import java.util.*

data class MazeEpisodeDTO(
    val id: Int?,
    val url: String?,
    val name: String?,
    val season: Int?,
    val number: Int?,
    val type: String?,
    val airdate: Date?,
    val airtime: String?,
    val airstamp: Date?,
    val rating: MazeRating?,
    val image: MazeImage?,
    val summary: String?,
    val _links: MazeLinks?
)