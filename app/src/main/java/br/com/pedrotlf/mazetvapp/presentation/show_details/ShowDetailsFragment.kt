package br.com.pedrotlf.mazetvapp.presentation.show_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import br.com.pedrotlf.mazetvapp.BaseFragment
import br.com.pedrotlf.mazetvapp.databinding.FragmentShowDetailsBinding
import br.com.pedrotlf.mazetvapp.domain.model.MazeEpisode
import br.com.pedrotlf.mazetvapp.domain.model.MazeShow
import br.com.pedrotlf.mazetvapp.presentation.MazeShowViewModel
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch

class ShowDetailsFragment: BaseFragment() {

    private var _binding: FragmentShowDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<MazeShowViewModel>()

    private val adapter by lazy {
        MazeEpisodeAdapter(object : MazeEpisodeViewHolder.OnItemClickListener {
            override fun onItemClick(episode: MazeEpisode) {
                viewModel.selectEpisode(episode)
                val navDirection = ShowDetailsFragmentDirections.actionShowDetailsFragmentToEpisodeDetailsFragment()
                findNavController().navigate(navDirection)
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentShowDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            rvEpisodesList.adapter = adapter
        }

        setupObservers()
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.selectedShowEpisodes.collect {
                    it.first?.let { show ->
                        setShowInfo(show)
                        adapter.submitMazeEpisodesList(it.second)
                    }
                }
            }
        }
    }

    private fun setShowInfo(show: MazeShow) {
        binding.apply {
            Glide.with(this@ShowDetailsFragment).load(show.posterImage).into(ivShowImage)

            var genresText = ""
            show.genres.forEachIndexed { i, txt ->
                genresText += txt
                if(i != show.genres.lastIndex)
                    genresText += ", "
            }
            tvShowDetailsGenres.text = genresText
            tvShowDetailsAirTimeText.text = show.airTime
            tvShowDetailsSummary.text = HtmlCompat.fromHtml(show.summary, HtmlCompat.FROM_HTML_MODE_LEGACY)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}