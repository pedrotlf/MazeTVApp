package br.com.pedrotlf.mazetvapp.domain.repository

import androidx.paging.Pager
import br.com.pedrotlf.mazetvapp.data.dto.MazeEpisodeDTO
import br.com.pedrotlf.mazetvapp.data.dto.MazeShowDTO

interface MazeRepository {

    fun getShows(query: String): Pager<Int, MazeShowDTO>

    suspend fun getEpisodesList(showId: Int): List<MazeEpisodeDTO>
}