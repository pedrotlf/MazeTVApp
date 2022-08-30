package br.com.pedrotlf.mazetvapp.presentation.show_details

import androidx.recyclerview.widget.RecyclerView
import br.com.pedrotlf.mazetvapp.R
import br.com.pedrotlf.mazetvapp.databinding.ItemSeparatorBinding

class SeparatorViewHolder(
    private val binding: ItemSeparatorBinding,
): RecyclerView.ViewHolder(binding.root) {

    fun bind(seasonNumber: Int) {
        binding.apply {
            tvSeasonNumber.text = root.context.getString(
                R.string.maze_show_details_season_number_text,
                seasonNumber
            )
        }
    }
}