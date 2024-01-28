package by.bashlikovvv.homescreen.presentation.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.map
import by.bashlikovvv.core.domain.model.Destination
import by.bashlikovvv.core.util.navigateToDestination
import by.bashlikovvv.homescreen.databinding.FragmentAllMoviesBinding
import by.bashlikovvv.homescreen.di.HomeScreenComponentViewModel
import by.bashlikovvv.homescreen.presentation.adapter.allmovies.AllMoviesListAdapter
import by.bashlikovvv.homescreen.presentation.adapter.allmovies.AllMoviesLoadStateAdapter
import by.bashlikovvv.homescreen.presentation.viewmodel.HomeScreenViewModel
import dagger.Lazy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class AllMoviesFragment : Fragment() {

    @Inject internal lateinit var viewModelFactory: Lazy<HomeScreenViewModel.Factory>

    private val viewModel: HomeScreenViewModel by viewModels({ requireActivity() }) {
        viewModelFactory.get()
    }

    private val adapter = AllMoviesListAdapter { movie ->
        navigateToDestination(Destination.MovieDetailsScreen(movie.id))
    }

    override fun onAttach(context: Context) {
        ViewModelProvider(this)[HomeScreenComponentViewModel::class.java]
            .homeScreenComponent
            .inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentAllMoviesBinding.inflate(inflater, container, false)

        setUpAllMoviesRecyclerView(binding)
        collectViewModelStates()

        return binding.root
    }

    private fun setUpAllMoviesRecyclerView(binding: FragmentAllMoviesBinding) {
        binding.allMoviesRecyclerView.adapter = adapter
            .withLoadStateFooter(AllMoviesLoadStateAdapter())
    }

    private fun collectViewModelStates() {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.moviesPagedData.collectLatest { pagedData ->
                adapter.submitData(pagedData)
            }
        }
    }

}