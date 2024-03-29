package by.bashlikovvv.homescreen.presentation.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import by.bashlikovvv.core.base.BaseFragment
import by.bashlikovvv.core.ext.dp
import by.bashlikovvv.core.ext.launchMain
import by.bashlikovvv.core.ext.navigateToFlow
import by.bashlikovvv.homescreen.R
import by.bashlikovvv.homescreen.databinding.FragmentHomeScreenBinding
import by.bashlikovvv.homescreen.di.HomeScreenComponentProvider
import by.bashlikovvv.homescreen.domain.model.CategoryLogo
import by.bashlikovvv.homescreen.domain.model.CategoryText
import by.bashlikovvv.homescreen.presentation.adapter.categories.CategoriesListAdapter
import by.bashlikovvv.homescreen.presentation.adapter.categories.CenterSnapHelper
import by.bashlikovvv.homescreen.presentation.viewmodel.HomeScreenViewModel
import dagger.Lazy
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import javax.inject.Inject

class HomeScreenFragment : BaseFragment<FragmentHomeScreenBinding>() {

    @Inject internal lateinit var viewModelFactory: Lazy<HomeScreenViewModel.Factory>

    private val viewModel: HomeScreenViewModel by viewModels({ requireActivity() }) {
        viewModelFactory.get()
    }

    private val navController: NavController
        get() = getHomeNavController()

    private val categoriesAdapter: CategoriesListAdapter = CategoriesListAdapter { category, position ->
        categoriesCenterSnapHelper.scrollTo(position, true)
        viewModel.navigateToCategory(category)
    }

    private val categoriesCenterSnapHelper: CenterSnapHelper = CenterSnapHelper()

    private val smoothScroller by lazy {
        object : LinearSmoothScroller(requireContext()) {

            var categoryMarginStart: Int = 0

            override fun getVerticalSnapPreference(): Int {
                return SNAP_TO_START
            }

            override fun calculateDyToMakeVisible(view: View?, snapPreference: Int): Int {
                return super.calculateDyToMakeVisible(view, snapPreference) + categoryMarginStart
            }
        }
    }

    override fun onAttach(context: Context) {
        (requireContext().applicationContext as HomeScreenComponentProvider)
            .provideHomeScreenComponent()
            .inject(this)
        super.onAttach(context)
    }

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeScreenBinding {
        return FragmentHomeScreenBinding.inflate(inflater, container, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        setUpCategoriesRecyclerView()
        setUpSwipeRefreshLayout()
        collectViewModelStates(categoriesAdapter)

        return binding.root
    }

    private fun setUpCategoriesRecyclerView() {
        categoriesAdapter.submitList(categories)
        binding.categoriesRecyclerView.let { categoriesRecyclerView ->
            categoriesCenterSnapHelper.attachToRecyclerView(categoriesRecyclerView)
            categoriesRecyclerView.onFlingListener = categoriesCenterSnapHelper
            categoriesRecyclerView.adapter = categoriesAdapter
        }
    }

    private fun setUpSwipeRefreshLayout() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            when(navController.currentDestination?.id) {
                R.id.allMoviesFragment -> {
                    viewModel.makeAllMoviesFata().invokeOnCompletion {
                        binding.swipeRefreshLayout.isRefreshing = false
                    }
                }
                R.id.moviesFragment -> {
//                    viewModel.makeMoviesData().invokeOnCompletion {
//                        binding.swipeRefreshLayout.isRefreshing = false
//                    }
                }
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun collectViewModelStates(
        categoriesAdapter: CategoriesListAdapter
    ) {
        launchMain(
            safeAction = {
                viewModel.moviesCurrentCategory
                    .debounce(500)
                    .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                    .collectLatest { category ->
                        if (category is CategoryText) {
                            categoriesAdapter.chooseCategory(
                                categoriesAdapter.getCategoryPosition(category.itemText)
                            )
                            smoothScrollToCategory(
                                category = category,
                                adapter = categoriesAdapter,
                                binding = binding
                            )
                        }
                    }
            },
            exceptionHandler = viewModel.exceptionsHandler
        )
        viewModel.navigateToCategoryLiveEvent.observe(viewLifecycleOwner) { category ->
            if (category == CategoryText(R.string.all)) {
                val currentDestinationId = navController.currentDestination?.id
                if (currentDestinationId == R.id.moviesFragment) {
                    navController.navigate(
                        R.id.action_moviesFragment_to_allMoviesFragment
                    )
                }
            } else {
                if (navController.currentDestination?.id == R.id.allMoviesFragment) {
                    navController.navigate(
                        R.id.action_allMoviesFragment_to_moviesFragment
                    )
                }
                viewModel.setCategory(category)
            }
        }
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
        viewModel.navigationFlowLiveEventDestinations.observe(viewLifecycleOwner) { destination ->
            navigateToFlow(destination)
        }
        viewModel.navigateToLiveEvent.observe(viewLifecycleOwner) { homeFlowDestination ->

        }
    }

    private fun getHomeNavController(): NavController {
        val navHostFragment = childFragmentManager
            .findFragmentById(R.id.homeScreenFragmentContainer) as NavHostFragment

        return navHostFragment.navController
    }

    private fun smoothScrollToCategory(
        category: CategoryText,
        adapter: CategoriesListAdapter,
        binding: FragmentHomeScreenBinding
    ) {
        val categoryMarginStart = try {
            binding.categoriesRecyclerView[0].width
        } catch (e: IndexOutOfBoundsException) {
            0.dp
        } + 100.dp

        val currentPos =
            (binding.categoriesRecyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        val targetPos = adapter.getCategoryPosition(category.itemText)

        val smoothSkipPos = if (targetPos - currentPos > 0) {
            maxOf(targetPos - 1, currentPos)
        } else {
            minOf(targetPos + 1, currentPos)
        }
        if (smoothSkipPos != currentPos) {
            (binding.categoriesRecyclerView.layoutManager as LinearLayoutManager).scrollToPosition(
                smoothSkipPos
            )
        }

        smoothScroller.categoryMarginStart = categoryMarginStart
        smoothScroller.targetPosition = targetPos
        binding.categoriesRecyclerView.layoutManager?.startSmoothScroll(smoothScroller)
    }

    companion object {
        val collections = listOf(
            CategoryText(R.string.top_250),
            CategoryText(R.string.top_500),
            CategoryText(R.string.most_popular),
            CategoryText(R.string.for_deaf)
        )
        val genres = listOf(
            CategoryText(R.string.comedies),
            CategoryText(R.string.cartoons),
            CategoryText(R.string.horrors),
            CategoryText(R.string.fantasy),
            CategoryText(R.string.thrillers),
            CategoryText(R.string.action_movies),
            CategoryText(R.string.melodramas),
            CategoryText(R.string.detectives),
            CategoryText(R.string.adventures),
            CategoryText(R.string.military),
            CategoryText(R.string.family),
            CategoryText(R.string.anime),
            CategoryText(R.string.historical),
            CategoryText(R.string.dramas)
        )

        val categories = listOf(
            CategoryLogo(R.drawable.kinopoisk_icon),
            CategoryText(R.string.all)
        ) + collections + genres
    }

}