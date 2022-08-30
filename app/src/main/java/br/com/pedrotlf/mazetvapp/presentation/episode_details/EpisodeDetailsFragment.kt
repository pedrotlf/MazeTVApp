package br.com.pedrotlf.mazetvapp.presentation.episode_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import br.com.pedrotlf.mazetvapp.BaseFragment
import br.com.pedrotlf.mazetvapp.R
import br.com.pedrotlf.mazetvapp.databinding.FragmentEpisodeDetailsBinding
import br.com.pedrotlf.mazetvapp.domain.model.MazeEpisode
import br.com.pedrotlf.mazetvapp.presentation.MazeShowViewModel
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch

class EpisodeDetailsFragment: BaseFragment() {

    private var _binding: FragmentEpisodeDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<MazeShowViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentEpisodeDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.selectedEpisode.collect {
                    it?.let { episode ->
                        setEpisodeInfo(episode)
                    }
                }
            }
        }
    }

    private fun setEpisodeInfo(episode: MazeEpisode) {
        binding.apply {
            Glide.with(this@EpisodeDetailsFragment).load(episode.image).into(ivEpisodeImage)

            tvEpisodeDetailsSeasonNumber.text = getString(R.string.maze_episode_details_season_number_text, episode.season)
            tvEpisodeDetailsEpisodeNumber.text = getString(R.string.maze_episode_details_episode_number_text, episode.number)
            tvEpisodeDetailsTitle.text = episode.name
            tvEpisodeDetailsSummary.text = HtmlCompat.fromHtml(episode.summary, HtmlCompat.FROM_HTML_MODE_LEGACY)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}