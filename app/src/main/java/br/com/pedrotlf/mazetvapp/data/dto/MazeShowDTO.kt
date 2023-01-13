package br.com.pedrotlf.mazetvapp.data.dto

import br.com.pedrotlf.mazetvapp.domain.model.MazeShow
import java.util.*

data class MazeShowDTO(
    val id: Int?,
    val url: String?,
    val name: String?,
    val type: String?,
    val language: String?,
    val genres: List<String>?,
    val status: String?,
    val runtime: Int?,
    val avarageRuntime: Int?,
    val premiered: Date?,
    val ended: Date?,
    val officialSite: String?,
    val schedule: MazeSchedule?,
    val rating: MazeRating?,
    val weight: Int?,
    val network: MazeNetwork?,
    //No examples found
//    val webChannel: ???,
//    val dvdCountry: ???,
    val externals: MazeExternals?,
    val image: MazeImage?,
    val summary: String?,
    val updated: Long?,
    val _links: MazeLinks?
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

data class MazeShowFromSearchDTO(
    val score: Double?,
    val show: MazeShowDTO?
)

data class MazeSchedule(
    val time: String?,
    val days: List<String>?
)

data class MazeRating(
    val avarage: Double?
)

data class MazeNetwork(
    val id: Int?,
    val name: String?,
    val country: MazeCountry?,
    val officialSite: String?
)

data class MazeCountry(
    val name: String?,
    val code: String?,
    val timezone: String?
)

data class MazeExternals(
    val tvrage: Int?,
    val thetvdb: Int?,
    val imdb: String?
)

data class MazeImage(
    val medium: String?,
    val original: String?
)

data class MazeLinks(
    val self: MazeLinkRef?,
    val previousepisode: MazeLinkRef?
)

data class MazeLinkRef(
    val href: String?
)