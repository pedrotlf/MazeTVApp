package br.com.pedrotlf.mazetvapp.domain.model

data class MazeEpisode(
    val id: Int,
    val name: String,
    val season: Int,
    val summary: String,
    val image: String?,
    val number: Int
)
