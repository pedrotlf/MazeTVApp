package br.com.pedrotlf.mazetvapp.presentation.show_details

import androidx.recyclerview.widget.RecyclerView
import br.com.pedrotlf.mazetvapp.R
import br.com.pedrotlf.mazetvapp.databinding.ItemMazeEpisodeBinding
import br.com.pedrotlf.mazetvapp.domain.model.MazeEpisode

class MazeEpisodeViewHolder(
    private val binding: ItemMazeEpisodeBinding,
): RecyclerView.ViewHolder(binding.root) {

    fun bind(ep: MazeEpisode, onClickListener: OnItemClickListener) {
        binding.apply {
            tvEpisodeNumber.text = root.context.getString(
                R.string.maze_show_details_episode_number_text,
                ep.number
            )
            tvEpisodeTitle.text = ep.name

            root.setOnClickListener {
                onClickListener.onItemClick(ep)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(episode: MazeEpisode)
    }
}