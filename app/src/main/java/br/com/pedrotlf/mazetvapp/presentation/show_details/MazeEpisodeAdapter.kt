package br.com.pedrotlf.mazetvapp.presentation.show_details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.pedrotlf.mazetvapp.R
import br.com.pedrotlf.mazetvapp.databinding.ItemMazeEpisodeBinding
import br.com.pedrotlf.mazetvapp.databinding.ItemSeparatorBinding
import br.com.pedrotlf.mazetvapp.domain.model.MazeEpisode

class MazeEpisodeAdapter(
    private val onClickListener: MazeEpisodeViewHolder.OnItemClickListener
): ListAdapter<EpisodeListUiModel, RecyclerView.ViewHolder>(EPISODE_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            R.layout.item_maze_episode -> MazeEpisodeViewHolder(
                ItemMazeEpisodeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            else -> SeparatorViewHolder(
                ItemSeparatorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if (holder is MazeEpisodeViewHolder) {
            holder.bind(
                (item as EpisodeListUiModel.EpisodeModel).episode,
                onClickListener
            )
        } else if (holder is SeparatorViewHolder) {
            holder.bind(
                (item as EpisodeListUiModel.SeparatorModel).seasonNumber
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is EpisodeListUiModel.EpisodeModel -> R.layout.item_maze_episode
            is EpisodeListUiModel.SeparatorModel -> R.layout.item_separator
            null -> throw IllegalStateException("Unknown view")
        }
    }

    fun submitMazeEpisodesList(list: List<MazeEpisode>?) {
        submitList(list?.toUiModelList())
    }

    private fun List<MazeEpisode>.toUiModelList(): List<EpisodeListUiModel> {
        val finalList = mutableListOf<EpisodeListUiModel>()
        val seasonEpisodes = mutableListOf<MazeEpisode>()
        var seasonNumber = -1

        forEach { ep ->
            //If season was changed, add the "separator" and then all episodes acumulated in
            // seasonEpisodes list. Finally we change the season number to the new one and clear
            // the list.
            if(ep.season != seasonNumber){
                if(seasonEpisodes.isNotEmpty()){
                    finalList.add(EpisodeListUiModel.SeparatorModel(seasonNumber))
                    finalList.addAll(
                        seasonEpisodes.map { EpisodeListUiModel.EpisodeModel(it) }
                    )
                }
                seasonNumber = ep.season
                seasonEpisodes.clear()
            }
            seasonEpisodes.add(ep)
        }
        //When iteration is finished we add the "separator" and then all episodes acumulated in
        // seasonEpisodes list.
        finalList.add(EpisodeListUiModel.SeparatorModel(seasonNumber))
        finalList.addAll(
            seasonEpisodes.map { EpisodeListUiModel.EpisodeModel(it) }
        )

        return finalList
    }

    companion object{
        val EPISODE_COMPARATOR = object : DiffUtil.ItemCallback<EpisodeListUiModel>() {
            override fun areItemsTheSame(
                oldItem: EpisodeListUiModel, newItem: EpisodeListUiModel
            ): Boolean {
                val isSameRepoItem = oldItem is EpisodeListUiModel.EpisodeModel
                        && newItem is EpisodeListUiModel.EpisodeModel
                        && oldItem.episode.id == newItem.episode.id

                val isSameSeparatorItem = oldItem is EpisodeListUiModel.SeparatorModel
                        && newItem is EpisodeListUiModel.SeparatorModel
                        && oldItem.seasonNumber == newItem.seasonNumber

                return isSameRepoItem || isSameSeparatorItem
            }


            override fun areContentsTheSame(
                oldItem: EpisodeListUiModel, newItem: EpisodeListUiModel
            ): Boolean =
                oldItem == newItem
        }
    }
}