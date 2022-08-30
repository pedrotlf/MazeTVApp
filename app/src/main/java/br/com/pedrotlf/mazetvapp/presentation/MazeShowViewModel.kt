package br.com.pedrotlf.mazetvapp.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import br.com.pedrotlf.mazetvapp.domain.model.MazeEpisode
import br.com.pedrotlf.mazetvapp.domain.model.MazeShow
import br.com.pedrotlf.mazetvapp.domain.use_case.GetEpisodesListUseCase
import br.com.pedrotlf.mazetvapp.domain.use_case.GetShowsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MazeShowViewModel @Inject constructor(
    getShowsUseCase: GetShowsUseCase,
    getEpisodesListUseCase: GetEpisodesListUseCase,
    state: SavedStateHandle
): ViewModel() {

    private val _searchQuery = state.getLiveData(CURRENT_QUERY, "")
    private val _selectedShow = MutableStateFlow<MazeShow?>(null)
    private val _selectedEpisode = MutableStateFlow<MazeEpisode?>(null)

    val allShowsPaginated = _searchQuery.asFlow().flatMapLatest {
        getShowsUseCase.invoke(it).cachedIn(viewModelScope)
    }

    val selectedShowEpisodes: Flow<Pair<MazeShow?, List<MazeEpisode>>> = _selectedShow.flatMapLatest { show ->
        if(show != null)
            getEpisodesListUseCase.invoke(show.id).map { show to it }
        else
            flow<Pair<MazeShow?, List<MazeEpisode>>> { emit(null to emptyList()) }
    }

    val selectedEpisode: Flow<MazeEpisode?> = _selectedEpisode

    fun searchShows(query: String) {
        val oldValue = _searchQuery.value
        if(query != oldValue)
            _searchQuery.value = query
    }

    fun selectShow(show: MazeShow) {
        _selectedShow.value = show
    }

    fun selectEpisode(episode: MazeEpisode) {
        _selectedEpisode.value = episode
    }

    companion object {
        private const val CURRENT_QUERY = "current_query"
    }

}