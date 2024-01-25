package by.bashlikovvv.homescreen.presentation.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import by.bashlikovvv.homescreen.R
import by.bashlikovvv.homescreen.databinding.FragmentHomeScreenBinding
import by.bashlikovvv.homescreen.di.HomeScreenComponentViewModel
import by.bashlikovvv.homescreen.domain.model.CategoryLogo
import by.bashlikovvv.homescreen.domain.model.CategoryText
import by.bashlikovvv.homescreen.presentation.adapter.categories.CategoriesListAdapter
import by.bashlikovvv.homescreen.presentation.adapter.categories.CenterSnapHelper
import by.bashlikovvv.homescreen.presentation.viewmodel.HomeScreenViewModel
import dagger.Lazy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


class HomeScreenFragment : Fragment() {

    @Inject internal lateinit var viewModelFactory: Lazy<HomeScreenViewModel.Factory>

    private val viewModel: HomeScreenViewModel by viewModels({ requireActivity() }) {
        viewModelFactory.get()
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
        val binding = FragmentHomeScreenBinding.inflate(inflater, container, false)

        setUpCategoriesRecyclerView(binding)
        setUpHomeScreenNavigation(binding)
        collectViewModelStates()

        return binding.root
    }

    private fun setUpCategoriesRecyclerView(binding: FragmentHomeScreenBinding) {
        binding.categoriesRecyclerView.let { categoriesRecyclerView ->
            val centerSnapHelper = CenterSnapHelper()
            val categoriesAdapter = CategoriesListAdapter { category, position ->
                centerSnapHelper.scrollTo(position, true)

            }.apply { submitList(categories) }
            centerSnapHelper.attachToRecyclerView(categoriesRecyclerView)
            categoriesRecyclerView.onFlingListener = centerSnapHelper
            categoriesRecyclerView.adapter = categoriesAdapter/*.withLoadStateFooter()*/
        }
    }

    private fun setUpHomeScreenNavigation(binding: FragmentHomeScreenBinding) {

    }

    private fun collectViewModelStates() {
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.moviesPagedData.collectLatest { pagedData ->

            }
        }
    }

    companion object {
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