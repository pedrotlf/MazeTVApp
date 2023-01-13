package br.com.pedrotlf.mazetvapp.domain.model

data class MazeShow(
    val id: Int,
    val name: String,
    val posterImage: String?,
    val airDays: List<String>,
    val airTime: String,
    val genres: List<String>,
    val summary: String
)
