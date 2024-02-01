package by.bashlikovvv.homescreen.presentation.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import by.bashlikovvv.core.ext.dp
import by.bashlikovvv.homescreen.R
import by.bashlikovvv.homescreen.databinding.FragmentHomeScreenBinding
import by.bashlikovvv.homescreen.di.HomeScreenComponentViewModel
import by.bashlikovvv.homescreen.domain.model.CategoryLogo
import by.bashlikovvv.homescreen.domain.model.CategoryText
import by.bashlikovvv.homescreen.presentation.adapter.categories.CategoriesListAdapter
import by.bashlikovvv.homescreen.presentation.adapter.categories.CenterSnapHelper
import by.bashlikovvv.homescreen.presentation.viewmodel.HomeScreenViewModel
import dagger.Lazy
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeScreenFragment : Fragment() {

    @Inject internal lateinit var viewModelFactory: Lazy<HomeScreenViewModel.Factory>

    private val viewModel: HomeScreenViewModel by viewModels({ requireActivity() }) {
        viewModelFactory.get()
    }

    private lateinit var navController: NavController

    private lateinit var categoriesAdapter: CategoriesListAdapter

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
        val binding = FragmentHomeScreenBinding.inflate(inflater, container, false)

        navController = getNavController()
        setUpCategoriesRecyclerView(binding)
        collectViewModelStates(categoriesAdapter, binding)

        return binding.root
    }

    private fun setUpCategoriesRecyclerView(binding: FragmentHomeScreenBinding) {
        val centerSnapHelper = CenterSnapHelper()
        categoriesAdapter = CategoriesListAdapter { category, position ->
            centerSnapHelper.scrollTo(position, true)
            viewModel.navigateToCategory(category)
        }.apply { submitList(categories) }
        binding.categoriesRecyclerView.let { categoriesRecyclerView ->
            centerSnapHelper.attachToRecyclerView(categoriesRecyclerView)
            categoriesRecyclerView.onFlingListener = centerSnapHelper
            categoriesRecyclerView.adapter = categoriesAdapter
        }
    }

    private fun collectViewModelStates(
        categoriesAdapter: CategoriesListAdapter,
        binding: FragmentHomeScreenBinding
    ) {
        lifecycleScope.launch {
            viewModel.moviesCurrentCategory.observe(viewLifecycleOwner) { category ->
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
        }
        viewModel.navigateToCategoryLiveEvent.observe(viewLifecycleOwner) { category ->
            if (category == CategoryText(R.string.all)) {
                if (navController.currentDestination?.id != R.id.allMoviesFragment) {
                    navController.navigate(
                        R.id.action_moviesFragment_to_allMoviesFragment
                    )
                }
            } else {
                if (navController.currentDestination?.id != R.id.moviesFragment) {
                    navController.navigate(
                        R.id.action_allMoviesFragment_to_moviesFragment
                    )
                }
                viewModel.setCategory(category)
            }
        }
    }

    private fun getNavController(): NavController {
        val navHostFragment = childFragmentManager
            .findFragmentById(R.id.homeScreenFragmentContainer) as NavHostFragment

        return navHostFragment.navController
    }
    private fun smoothScrollToCategory(
        category: CategoryText,
        adapter: CategoriesListAdapter,
        binding: FragmentHomeScreenBinding
    ) {
        val categoryMarginStart = binding.categoriesRecyclerView[0].width + 100.dp

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

        val smoothScroller = object : LinearSmoothScroller(requireContext()) {
            override fun getVerticalSnapPreference(): Int {
                return SNAP_TO_START
            }

            override fun calculateDyToMakeVisible(view: View?, snapPreference: Int): Int {
                return super.calculateDyToMakeVisible(view, snapPreference) + categoryMarginStart
            }
        }
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