package br.com.pedrotlf.mazetvapp.presentation.show_details

import br.com.pedrotlf.mazetvapp.domain.model.MazeEpisode

sealed class EpisodeListUiModel {
    data class EpisodeModel(val episode: MazeEpisode) : EpisodeListUiModel()

    data class SeparatorModel(val seasonNumber: Int) : EpisodeListUiModel()
}
