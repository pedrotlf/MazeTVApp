package br.com.pedrotlf.mazetvapp.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.pedrotlf.mazetvapp.databinding.ItemMazeShowListFooterBinding

class MazeShowLoadStateAdapter(
    private val retry: () -> Unit
): LoadStateAdapter<MazeShowLoadStateAdapter.MazeShowLoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): MazeShowLoadStateViewHolder {
        val binding = ItemMazeShowListFooterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MazeShowLoadStateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MazeShowLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }


    inner class MazeShowLoadStateViewHolder(
        private val binding: ItemMazeShowListFooterBinding
    ): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnRetry.setOnClickListener {
                retry.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            binding.apply {
                pbFooterLoading.isVisible = loadState is LoadState.Loading
                btnRetry.isVisible = loadState !is LoadState.Loading
                tvError.isVisible = loadState !is LoadState.Loading
            }
        }
    }
}