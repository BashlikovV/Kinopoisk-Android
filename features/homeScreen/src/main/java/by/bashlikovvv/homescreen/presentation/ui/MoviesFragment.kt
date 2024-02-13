package by.bashlikovvv.homescreen.presentation.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import by.bashlikovvv.core.domain.model.Destination
import by.bashlikovvv.core.domain.model.Movie
import by.bashlikovvv.core.ext.dp
import by.bashlikovvv.homescreen.databinding.FragmentHomeScreenBinding
import by.bashlikovvv.homescreen.databinding.FragmentMoviesBinding
import by.bashlikovvv.homescreen.di.HomeScreenComponentViewModel
import by.bashlikovvv.homescreen.domain.model.CategoryMore
import by.bashlikovvv.homescreen.domain.model.CategoryText
import by.bashlikovvv.homescreen.domain.model.CategoryTitle
import by.bashlikovvv.homescreen.presentation.adapter.movies.MoviesListAdapter
import by.bashlikovvv.homescreen.presentation.viewmodel.HomeScreenViewModel
import dagger.Lazy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

class MoviesFragment : Fragment() {

    @Inject internal lateinit var viewModelFactory: Lazy<HomeScreenViewModel.Factory>

    private val viewModel: HomeScreenViewModel by viewModels({ requireActivity() }) {
        viewModelFactory.get()
    }

    private lateinit var binding: FragmentMoviesBinding

    private val adapter = MoviesListAdapter(
        clickListener = object : MoviesListAdapter.MoviesListAdapterClickListener {
            override fun notifyMovieClicked(movie: Movie) {
                viewModel.navigateToDestination(Destination.MovieDetailsScreen(movie.id))
            }

            override fun notifyMoreClicked(categoryMore: CategoryMore) {
                viewModel.navigateToDestination(
                    Destination.MoreScreen(
                        viewModel.getGenreOrCollectionRequestNameByResId(categoryMore.categoryName)
                    )
                )
            }

            override fun notifyBookmarkClicked(movie: Movie) {
                viewModel.onBookmarkClicked(movie)
            }
        }
    )

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
        binding = FragmentMoviesBinding.inflate(inflater, container, false)

        collectViewModelStates()
        setUpMoviesRecyclerView()

        return binding.root
    }

    @OptIn(FlowPreview::class)
    private fun collectViewModelStates() {
        lifecycleScope.launch {
            viewModel.moviesUpdateState
                .debounce(500)
                .collectLatest {
                    if (it) {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.moviesRecyclerView.visibility = View.GONE
                    } else {
                        binding.progressBar.visibility = View.GONE
                        binding.moviesRecyclerView.visibility = View.VISIBLE
                    }
                }
        }
        lifecycleScope.launch {
            viewModel.currentCategory.collectLatest { category ->
                smoothScrollToCategory(category as CategoryText)
            }
        }
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.moviesData.collectLatest { moviesCategory ->
                adapter.submitList(moviesCategory)
            }
        }
    }

    private fun setUpMoviesRecyclerView() {
        binding.moviesRecyclerView.adapter = adapter
        binding.moviesRecyclerView
            .setOnScrollChangeListener { _, _, _, _, _ ->
                val layoutManager = binding.moviesRecyclerView.layoutManager as LinearLayoutManager
                val position = layoutManager.findLastVisibleItemPosition()
                val categoryPosition = adapter.getCategoryPositionByPosition(position)
                val item = adapter.currentList.getOrNull(categoryPosition ?: return@setOnScrollChangeListener)
                if (item is CategoryTitle && item != viewModel.moviesCurrentCategory.value) {
                    viewModel.setMoviesCurrentCategory(item)
                }
            }
    }

    private fun smoothScrollToCategory(category: CategoryText) {
        val toolbarHeight = FragmentHomeScreenBinding.inflate(requireParentFragment().layoutInflater)
            .categoriesCardView.height
        val categoryMarginTop = toolbarHeight + 24.dp

        val currentPos =
            (binding.moviesRecyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        val targetPos = adapter.getCategoryPosition(CategoryTitle(category.itemText))

        val smoothSkipPos = if (targetPos - currentPos > 0) {
            maxOf(targetPos - CATEGORY_SCROLL_ITEMS_COUNT, currentPos)
        } else {
            minOf(targetPos + CATEGORY_SCROLL_ITEMS_COUNT, currentPos)
        }
        if (smoothSkipPos != currentPos) {
            (binding.moviesRecyclerView.layoutManager as LinearLayoutManager).scrollToPosition(
                smoothSkipPos
            )
        }

        val smoothScroller = object : LinearSmoothScroller(requireContext()) {
            override fun getVerticalSnapPreference(): Int {
                return SNAP_TO_START
            }

            override fun calculateDyToMakeVisible(view: View?, snapPreference: Int): Int {
                return super.calculateDyToMakeVisible(view, snapPreference) + categoryMarginTop
            }
        }
        smoothScroller.targetPosition = targetPos
        binding.moviesRecyclerView.layoutManager?.startSmoothScroll(smoothScroller)
    }

    companion object {
        private const val CATEGORY_SCROLL_ITEMS_COUNT = 15
    }

}