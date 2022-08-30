package br.com.pedrotlf.mazetvapp.presentation.home

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.pedrotlf.mazetvapp.databinding.ItemMazeShowBinding
import br.com.pedrotlf.mazetvapp.domain.model.MazeShow
import com.bumptech.glide.Glide

class MazeShowViewHolder(
    private val binding: ItemMazeShowBinding,
): RecyclerView.ViewHolder(binding.root) {

    fun bind(show: MazeShow, onClickListener: OnItemClickListener) {
        binding.apply {
            tvShowTitle.text = show.name
            Glide.with(root.context)
                .load(show.posterImage)
                .optionalCenterCrop()
                .into(ivShowImage)

            cvShowCardLayout.setOnClickListener {
                onClickListener.onItemClick(show)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(show: MazeShow)
    }

    companion object{
        val SHOW_COMPARATOR = object : DiffUtil.ItemCallback<MazeShow>() {
            override fun areItemsTheSame(oldItem: MazeShow, newItem: MazeShow): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: MazeShow, newItem: MazeShow): Boolean =
                oldItem == newItem
        }
    }
}