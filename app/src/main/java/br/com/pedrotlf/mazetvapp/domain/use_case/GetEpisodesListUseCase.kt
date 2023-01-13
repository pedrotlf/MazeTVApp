package br.com.pedrotlf.mazetvapp.domain.use_case

import br.com.pedrotlf.mazetvapp.domain.model.MazeEpisode
import br.com.pedrotlf.mazetvapp.data.dto.toMazeEpisode
import br.com.pedrotlf.mazetvapp.domain.repository.MazeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetEpisodesListUseCase @Inject constructor(
    private val mazeRepository: MazeRepository
) {

    operator fun invoke(showId: Int): Flow<List<MazeEpisode>> {
        return flow {
            val response = mazeRepository.getEpisodesList(showId).map {
                it.toMazeEpisode()
            }
            emit(response)
        }
    }
}