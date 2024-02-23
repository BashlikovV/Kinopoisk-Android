package by.bashlikovvv.homescreen.presentation.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import by.bashlikovvv.core.domain.model.Destination
import by.bashlikovvv.core.domain.model.Movie
import by.bashlikovvv.homescreen.databinding.FragmentAllMoviesBinding
import by.bashlikovvv.homescreen.di.HomeScreenComponentProvider
import by.bashlikovvv.homescreen.presentation.adapter.allmovies.AllMoviesListAdapter
import by.bashlikovvv.homescreen.presentation.adapter.allmovies.AllMoviesLoadStateAdapter
import by.bashlikovvv.homescreen.presentation.viewmodel.HomeScreenViewModel
import dagger.Lazy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

class AllMoviesFragment : Fragment() {

    @Inject internal lateinit var viewModelFactory: Lazy<HomeScreenViewModel.Factory>

    private val viewModel: HomeScreenViewModel by viewModels({ requireActivity() }) {
        viewModelFactory.get()
    }

    private val adapter = AllMoviesListAdapter(
        onClickListener = object : AllMoviesListAdapter.AllMoviesListAdapterClickListener {
            override fun notifyMovieClicked(movie: Movie) {
                viewModel.navigateToDestination(Destination.MovieDetailsScreen(movie.id))
            }

            override fun notifyBookmarkClicked(movie: Movie) {
                viewModel.onBookmarkClicked(movie)
            }
        }
    )

    override fun onAttach(context: Context) {
        (requireContext().applicationContext as HomeScreenComponentProvider)
            .provideHomeScreenComponent()
            .inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentAllMoviesBinding.inflate(inflater, container, false)

        setUpAllMoviesRecyclerView(binding)
        collectViewModelStates(binding)

        return binding.root
    }

    private fun setUpAllMoviesRecyclerView(binding: FragmentAllMoviesBinding) {
        binding.allMoviesRecyclerView.adapter = adapter
            .withLoadStateFooter(AllMoviesLoadStateAdapter())
    }

    @OptIn(FlowPreview::class)
    private fun collectViewModelStates(binding: FragmentAllMoviesBinding) {
        lifecycleScope.launch {
            viewModel.allMoviesUpdateState
                .debounce(500)
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collectLatest {
                    if (it) {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.allMoviesRecyclerView.visibility = View.GONE
                    } else {
                        binding.progressBar.visibility = View.GONE
                        binding.allMoviesRecyclerView.visibility = View.VISIBLE
                    }
                }
        }
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.moviesPagedData.collectLatest { pagedData ->
                adapter.submitData(pagedData)
            }
        }
        lifecycleScope.launch {
            adapter.loadStateFlow
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collectLatest {
                if (it.append is LoadState.Loading && adapter.itemCount > 0) {
                    viewModel.setAllMoviesProgress(true)
                } else {
                    viewModel.setAllMoviesProgress(false)
                }
            }
        }
    }

}