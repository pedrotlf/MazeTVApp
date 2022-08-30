package br.com.pedrotlf.mazetvapp.presentation.home

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import br.com.pedrotlf.mazetvapp.BaseFragment
import br.com.pedrotlf.mazetvapp.R
import br.com.pedrotlf.mazetvapp.databinding.FragmentHomeBinding
import br.com.pedrotlf.mazetvapp.domain.model.MazeShow
import br.com.pedrotlf.mazetvapp.presentation.MazeShowViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment: BaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<MazeShowViewModel>()

    private val allShowsAdapter by lazy {
        MazeShowPaginatedAdapter(object : MazeShowViewHolder.OnItemClickListener {
            override fun onItemClick(show: MazeShow) {
                viewModel.selectShow(show)
                val navDirection = HomeFragmentDirections.actionHomeFragmentToShowDetailsFragment()
                findNavController().navigate(navDirection)
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupMenu()

        binding.apply {
            rvShowsList.setHasFixedSize(true)
            rvShowsList.adapter = allShowsAdapter.withLoadStateHeaderAndFooter(
                header = MazeShowLoadStateAdapter { allShowsAdapter.retry() },
                footer = MazeShowLoadStateAdapter { allShowsAdapter.retry() }
            )

            btnRetry.setOnClickListener {
                allShowsAdapter.retry()
            }

            setupObservers()
        }
    }

    private fun FragmentHomeBinding.setupObservers() {
        observeShowsListPagingData()
        observePagingAdapterState()
    }

    private fun FragmentHomeBinding.observePagingAdapterState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                allShowsAdapter.loadStateFlow.collectLatest {
                    pbListLoadingView.isVisible = it.source.refresh is LoadState.Loading
                    rvShowsList.isVisible = it.source.refresh is LoadState.NotLoading
                    tvListError.isVisible = it.source.refresh is LoadState.Error
                    btnRetry.isVisible = it.source.refresh is LoadState.Error

                    if(it.source.refresh is LoadState.NotLoading &&
                        it.append.endOfPaginationReached
                        && allShowsAdapter.itemCount < 1) {
                        rvShowsList.isVisible = false
                        tvListEmpty.isVisible = true
                    } else {
                        tvListEmpty.isVisible = false
                    }
                }
            }
        }
    }

    private fun observeShowsListPagingData() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.allShowsPaginated.collect {
                    binding.rvShowsList.scrollToPosition(0)
                    allShowsAdapter.submitData(viewLifecycleOwner.lifecycle, it)
                }
            }
        }
    }

    private fun setupMenu() {
        activity?.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_gallery, menu)

                val searchItem = menu.findItem(R.id.menu_action_search)
                val searchView = searchItem.actionView as? SearchView

                searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        if(query != null && query.length >= 2) {
                            viewModel.searchShows(query)
                            searchView.clearFocus()
                        }
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        return true
                    }

                })

                searchView?.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
                    override fun onViewAttachedToWindow(p0: View?) { }
                    override fun onViewDetachedFromWindow(p0: View?) {
                        //When fragment is not visible, it means searchview was detached because of
                        // a different interaction other then closing search.
                        if(isVisible)
                            viewModel.searchShows("")
                    }
                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}