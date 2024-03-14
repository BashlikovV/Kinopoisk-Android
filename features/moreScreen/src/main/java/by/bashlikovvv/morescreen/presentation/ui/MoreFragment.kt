package by.bashlikovvv.morescreen.presentation.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import by.bashlikovvv.core.base.BaseFragment
import by.bashlikovvv.core.domain.model.FlowDestinations
import by.bashlikovvv.core.domain.model.Movie
import by.bashlikovvv.core.ext.launchIO
import by.bashlikovvv.core.ext.launchMain
import by.bashlikovvv.core.ext.navigateToFlow
import by.bashlikovvv.morescreen.databinding.FragmentMoreBinding
import by.bashlikovvv.morescreen.di.MoreScreenComponentProvider
import by.bashlikovvv.morescreen.presentation.ui.adapter.MoviesListAdapter
import by.bashlikovvv.morescreen.presentation.viewmodel.MoreFragmentViewModel
import dagger.Lazy
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class MoreFragment : BaseFragment<FragmentMoreBinding>() {

    private var categoryName: String? = null

    @Inject internal lateinit var viewModelFactory: Lazy<MoreFragmentViewModel.Factory>

    private val viewModel: MoreFragmentViewModel by viewModels {
        viewModelFactory.get()
    }

    private val adapter: MoviesListAdapter by lazy {
        MoviesListAdapter(
            onClickListener = object : MoviesListAdapter.MoviesListAdapterClickListener {
                override fun notifyMovieClicked(movie: Movie) {
                    viewModel.navigateToFlow(FlowDestinations.DetailsScreen(movie.id))
                }
            }
        )
    }

    override fun onAttach(context: Context) {
        (requireContext().applicationContext as MoreScreenComponentProvider)
            .provideMoreScreenComponent()
            .inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryName = requireArguments().getString(KEY_CATEGORY_NAME)
    }

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMoreBinding {
        return FragmentMoreBinding.inflate(inflater, container, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        collectViewModelStates()
        setUpMoviesRecyclerView()
        setUpSearchView(binding.searchView)

        return binding.root
    }

    private fun collectViewModelStates() {
        viewModel.navigationFlowLiveEventDestinations.observe(viewLifecycleOwner) {
            navigateToFlow(it)
        }
        launchIO(
            safeAction = {
                viewModel
                    .getMovies(categoryName ?: return@launchIO)
                    .collectLatest { pagingData ->
                        adapter.submitData(pagingData)
                    }
            },
            exceptionHandler = viewModel.exceptionsHandler
        )
        launchMain(
            safeAction = {
                viewModel.exceptionsFlow
                    .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                    .collectLatest {
                        it
                            .getAlertDialog(requireContext())
                            .show()
                    }
            },
            exceptionHandler = viewModel.exceptionsHandler
        )
    }

    private fun setUpMoviesRecyclerView() {
        binding.moviesRecyclerView.adapter = adapter
    }

    private fun setUpSearchView(searchView: SearchView) {
        with(searchView) {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    viewModel.onSearch(query ?: "")
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    viewModel.onSearch(newText ?: "")
                    return true
                }
            })
            setOnSearchClickListener { viewModel.onSearch("") }
            setOnClickListener { searchView.isIconified = false }
        }
    }

    companion object {

        const val KEY_CATEGORY_NAME: String = "key_category_name"

    }
}