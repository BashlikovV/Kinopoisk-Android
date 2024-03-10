package by.bashlikovvv.morescreen.presentation.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import by.bashlikovvv.core.base.BaseBottomSheetDialogFragment
import by.bashlikovvv.core.domain.model.Destination
import by.bashlikovvv.core.domain.model.Movie
import by.bashlikovvv.core.ext.launchIO
import by.bashlikovvv.core.ext.launchMain
import by.bashlikovvv.core.ext.navigateToDestination
import by.bashlikovvv.morescreen.databinding.FragmentMoreBinding
import by.bashlikovvv.morescreen.di.MoreScreenComponentProvider
import by.bashlikovvv.morescreen.presentation.ui.adapter.MoviesListAdapter
import by.bashlikovvv.morescreen.presentation.viewmodel.MoreFragmentViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.Lazy
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class MoreFragment : BaseBottomSheetDialogFragment<FragmentMoreBinding>() {

    private var categoryName: String? = null

    @Inject internal lateinit var viewModelFactory: Lazy<MoreFragmentViewModel.Factory>

    private val viewModel: MoreFragmentViewModel by viewModels {
        viewModelFactory.get()
    }

    private val adapter: MoviesListAdapter by lazy {
        MoviesListAdapter(
            onClickListener = object : MoviesListAdapter.MoviesListAdapterClickListener {
                override fun notifyMovieClicked(movie: Movie) {
                    viewModel.navigateToDestination(Destination.MovieDetailsScreen(movie.id))
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

    override fun onStart() {
        super.onStart()
        val density = requireContext().resources.displayMetrics.density
        dialog?.let {
            val bottomSheet = it
                .findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
            val behavior = BottomSheetBehavior.from(bottomSheet)

            behavior.peekHeight = (COLLAPSED_HEIGHT * density).toInt()
            behavior.state = BottomSheetBehavior.STATE_COLLAPSED

//            behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
//                override fun onStateChanged(bottomSheet: View, newState: Int) {  }
//
//                override fun onSlide(bottomSheet: View, slideOffset: Float) {
//                    val params = MarginLayoutParams(binding.moviesRecyclerView.layoutParams)
//
//                    if (slideOffset > 0) {
//                        params.setMargins(
//                            params.leftMargin,
//                            params.topMargin - 25,
//                            params.rightMargin,
//                            params.bottomMargin
//                        )
//                    } else {
//                        params.setMargins(
//                            params.leftMargin,
//                            params.topMargin + 25,
//                            params.rightMargin,
//                            params.bottomMargin
//                        )
//                    }
//
//                    binding.moviesRecyclerView.layoutParams = params
//                }
//            })
        }
    }

    private fun collectViewModelStates() {
        viewModel.navigationDestinationLiveEvent.observe(viewLifecycleOwner) {
            navigateToDestination(it)
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

        // root padding (5dp) + searchView height (50dp) + searchView marginTop (15dp) + 5dp
        const val COLLAPSED_HEIGHT = 105
    }
}