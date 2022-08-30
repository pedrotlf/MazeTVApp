package br.com.pedrotlf.mazetvapp.domain.use_case

import androidx.paging.PagingData
import androidx.paging.map
import br.com.pedrotlf.mazetvapp.domain.model.MazeShow
import br.com.pedrotlf.mazetvapp.domain.model.toMazeShow
import br.com.pedrotlf.mazetvapp.domain.repository.MazeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetShowsUseCase @Inject constructor(
    private val mazeRepository: MazeRepository
) {

    operator fun invoke(query: String): Flow<PagingData<MazeShow>> {
        return mazeRepository.getShows(query).flow.map { pagingData ->
            pagingData.map { it.toMazeShow() }
        }
    }
}