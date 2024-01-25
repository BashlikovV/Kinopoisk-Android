package by.bashlikovvv.homescreen.presentation.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingDataAdapter
import by.bashlikovvv.core.domain.model.Movie
import by.bashlikovvv.homescreen.R
import by.bashlikovvv.homescreen.databinding.FragmentAllMoviesBinding
import by.bashlikovvv.homescreen.di.HomeScreenComponentViewModel
import by.bashlikovvv.homescreen.presentation.adapter.allmovies.AllMoviesItemViewHolder
import by.bashlikovvv.homescreen.presentation.adapter.allmovies.AllMoviesListAdapter
import by.bashlikovvv.homescreen.presentation.viewmodel.HomeScreenViewModel
import dagger.Lazy
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class AllMoviesFragment : Fragment() {

    @Inject internal lateinit var viewModelFactory: Lazy<HomeScreenViewModel.Factory>

    private val viewModel: HomeScreenViewModel by viewModels({ requireActivity() }) {
        viewModelFactory.get()
    }

    private val adapter = AllMoviesListAdapter { movie ->

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
        collectViewModelStates(binding)

        return binding.root
    }

    private fun setUpAllMoviesRecyclerView(binding: FragmentAllMoviesBinding) {
        binding.allMoviesRecyclerView.adapter = adapter
    }

    private fun collectViewModelStates(binding: FragmentAllMoviesBinding) {
        lifecycleScope.launch {
            viewModel.moviesPagedData.collectLatest { pagedData ->
                adapter.submitData(pagedData)
            }
        }
    }

}