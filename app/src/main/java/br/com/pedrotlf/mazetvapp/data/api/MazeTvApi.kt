package br.com.pedrotlf.mazetvapp.data.api

import br.com.pedrotlf.mazetvapp.data.dto.MazeEpisodeDTO
import br.com.pedrotlf.mazetvapp.data.dto.MazeShowDTO
import br.com.pedrotlf.mazetvapp.data.dto.MazeShowFromSearchDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MazeTvApi {

    companion object {
        const val BASE_URL = "https://api.tvmaze.com/"
    }

    @GET("search/shows")
    suspend fun searchShowByName(
        @Query("q") name: String
    ): List<MazeShowFromSearchDTO>

    @GET("shows/{showId}/episodes")
    suspend fun getEpisodes(
        @Path("showId") id: Int
    ): List<MazeEpisodeDTO>

    @GET("shows")
    suspend fun allShows(
        @Query("page") page: Int
    ): List<MazeShowDTO>
}