package br.com.pedrotlf.mazetvapp.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import br.com.pedrotlf.mazetvapp.databinding.ItemMazeShowBinding
import br.com.pedrotlf.mazetvapp.domain.model.MazeShow

class MazeShowPaginatedAdapter(
    private val onClickListener: MazeShowViewHolder.OnItemClickListener
): PagingDataAdapter<MazeShow, MazeShowViewHolder>(MazeShowViewHolder.SHOW_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MazeShowViewHolder {
        val binding = ItemMazeShowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MazeShowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MazeShowViewHolder, position: Int) {
        val currentItem = getItem(position)
        if(currentItem != null){
            holder.bind(currentItem, onClickListener)
        }
    }
}