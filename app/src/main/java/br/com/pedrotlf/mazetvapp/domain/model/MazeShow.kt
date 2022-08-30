package br.com.pedrotlf.mazetvapp.domain.model

import br.com.pedrotlf.mazetvapp.data.dto.MazeShowDTO

data class MazeShow(
    val id: Int,
    val name: String,
    val posterImage: String?,
    val airDays: List<String>,
    val airTime: String,
    val genres: List<String>,
    val summary: String
)

fun MazeShowDTO.toMazeShow(): MazeShow {
    return MazeShow(
        id = id ?: 0,
        name = name.orEmpty(),
        posterImage = image?.original ?: image?.medium,
        airDays = schedule?.days.orEmpty(),
        airTime = schedule?.time.orEmpty(),
        genres = genres.orEmpty(),
        summary = summary.orEmpty()
    )
}
