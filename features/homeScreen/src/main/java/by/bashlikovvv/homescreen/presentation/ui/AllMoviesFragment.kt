package by.bashlikovvv.homescreen.presentation.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.paging.LoadState
import by.bashlikovvv.core.base.BaseFragment
import by.bashlikovvv.core.domain.model.FlowDestinations
import by.bashlikovvv.core.domain.model.Movie
import by.bashlikovvv.core.ext.launchIO
import by.bashlikovvv.core.ext.launchMain
import by.bashlikovvv.homescreen.databinding.FragmentAllMoviesBinding
import by.bashlikovvv.homescreen.di.HomeScreenComponentProvider
import by.bashlikovvv.homescreen.presentation.adapter.allmovies.AllMoviesListAdapter
import by.bashlikovvv.homescreen.presentation.adapter.allmovies.AllMoviesLoadStateAdapter
import by.bashlikovvv.homescreen.presentation.viewmodel.HomeScreenViewModel
import dagger.Lazy
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import javax.inject.Inject

class AllMoviesFragment : BaseFragment<FragmentAllMoviesBinding>() {

    @Inject internal lateinit var viewModelFactory: Lazy<HomeScreenViewModel.Factory>

    private val viewModel: HomeScreenViewModel by viewModels({ requireActivity() }) {
        viewModelFactory.get()
    }

    private val adapter = AllMoviesListAdapter(
        onClickListener = object : AllMoviesListAdapter.AllMoviesListAdapterClickListener {
            override fun notifyMovieClicked(movie: Movie) {
                viewModel.navigateToFlow(FlowDestinations.DetailsScreen(movie.id))
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

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAllMoviesBinding {
        return FragmentAllMoviesBinding.inflate(inflater, container, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        setUpAllMoviesRecyclerView()
        collectViewModelStates()

        return binding.root
    }

    private fun setUpAllMoviesRecyclerView() {
        binding.allMoviesRecyclerView.adapter = adapter
            .withLoadStateFooter(AllMoviesLoadStateAdapter())
    }

    @OptIn(FlowPreview::class)
    private fun collectViewModelStates() {
        launchMain(
            safeAction = {
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
            },
            exceptionHandler = viewModel.exceptionsHandler
        )
        launchIO(
            safeAction = {
                viewModel.moviesPagedData.collectLatest { pagedData ->
                    adapter.submitData(pagedData)
                }
            },
            exceptionHandler = viewModel.exceptionsHandler
        )
        launchMain(
            safeAction = {
                adapter.loadStateFlow
                    .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                    .collectLatest {
                        if (it.append is LoadState.Loading && adapter.itemCount > 0) {
                            viewModel.setAllMoviesProgress(true)
                        } else {
                            viewModel.setAllMoviesProgress(false)
                        }
                    }
            },
            exceptionHandler = viewModel.exceptionsHandler
        )
    }

}