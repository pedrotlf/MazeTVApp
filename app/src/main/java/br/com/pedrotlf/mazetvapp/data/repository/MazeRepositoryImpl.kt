package br.com.pedrotlf.mazetvapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import br.com.pedrotlf.mazetvapp.data.api.MazeTvApi
import br.com.pedrotlf.mazetvapp.data.dto.MazeEpisodeDTO
import br.com.pedrotlf.mazetvapp.data.dto.MazeShowDTO
import br.com.pedrotlf.mazetvapp.domain.repository.MazeRepository

class MazeRepositoryImpl(private val mazeTvApi: MazeTvApi): MazeRepository {

    override fun getShows(query: String): Pager<Int, MazeShowDTO> {
        return Pager(
            config = PagingConfig(
                pageSize = MAZETV_PAGE_SIZE,
                maxSize = MAZETV_PAGE_SIZE * 3,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MazePagingSource(mazeTvApi, query) }
        )
    }

    override suspend fun getEpisodesList(showId: Int): List<MazeEpisodeDTO> {
        return mazeTvApi.getEpisodes(showId)
    }
}