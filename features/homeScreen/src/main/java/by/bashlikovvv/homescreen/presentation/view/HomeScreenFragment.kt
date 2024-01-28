package by.bashlikovvv.homescreen.presentation.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import by.bashlikovvv.homescreen.R
import by.bashlikovvv.homescreen.databinding.FragmentHomeScreenBinding
import by.bashlikovvv.homescreen.di.HomeScreenComponentViewModel
import by.bashlikovvv.homescreen.domain.model.Category
import by.bashlikovvv.homescreen.domain.model.CategoryLogo
import by.bashlikovvv.homescreen.domain.model.CategoryText
import by.bashlikovvv.homescreen.presentation.adapter.categories.CategoriesListAdapter
import by.bashlikovvv.homescreen.presentation.adapter.categories.CenterSnapHelper
import by.bashlikovvv.homescreen.presentation.viewmodel.HomeScreenViewModel
import dagger.Lazy
import javax.inject.Inject

class HomeScreenFragment : Fragment() {

    @Inject internal lateinit var viewModelFactory: Lazy<HomeScreenViewModel.Factory>

    private val viewModel: HomeScreenViewModel by viewModels({ requireActivity() }) {
        viewModelFactory.get()
    }

    private lateinit var navController: NavController

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

        return binding.root
    }

    private fun setUpCategoriesRecyclerView(binding: FragmentHomeScreenBinding) {
        binding.categoriesRecyclerView.let { categoriesRecyclerView ->
            val centerSnapHelper = CenterSnapHelper()
            val categoriesAdapter = CategoriesListAdapter { category, position ->
                centerSnapHelper.scrollTo(position, true)
                navigateToCategory(category)
            }.apply { submitList(categories) }
            centerSnapHelper.attachToRecyclerView(categoriesRecyclerView)
            categoriesRecyclerView.onFlingListener = centerSnapHelper
            categoriesRecyclerView.adapter = categoriesAdapter
        }
    }

    private fun getNavController(): NavController {
        val navHostFragment = childFragmentManager
            .findFragmentById(R.id.homeScreenFragmentContainer) as NavHostFragment

        return navHostFragment.navController
    }

    private fun navigateToCategory(category: Category) {
        when(category) {
            CategoryText(R.string.all) -> {
                if (navController.currentDestination?.id != R.id.allMoviesFragment) {
                    navController.navigate(R.id.allMoviesFragment)
                }
            }
            else -> {
                if (navController.currentDestination?.id != R.id.moviesFragment) {
                    navController.navigate(R.id.moviesFragment)
                }
                viewModel.setCategory(category)
            }
        }
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
            CategoryText(R.string.all),
            CategoryText(R.string.top_250),
            CategoryText(R.string.top_500),
            CategoryText(R.string.most_popular),
            CategoryText(R.string.for_deaf),
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

    }

}