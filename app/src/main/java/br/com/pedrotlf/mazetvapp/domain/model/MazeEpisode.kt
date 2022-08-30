package br.com.pedrotlf.mazetvapp.domain.model

import br.com.pedrotlf.mazetvapp.data.dto.MazeEpisodeDTO

data class MazeEpisode(
    val id: Int,
    val name: String,
    val season: Int,
    val summary: String,
    val image: String?,
    val number: Int
)

fun MazeEpisodeDTO.toMazeEpisode(): MazeEpisode {
    return MazeEpisode(
        id = id ?: 0,
        name = name.orEmpty(),
        season = season ?: 0,
        summary = summary.orEmpty(),
        image = image?.medium ?: image?.original,
        number = number ?: 0
    )
}
