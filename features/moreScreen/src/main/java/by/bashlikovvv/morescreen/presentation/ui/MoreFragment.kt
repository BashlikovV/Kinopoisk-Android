package by.bashlikovvv.morescreen.presentation.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import by.bashlikovvv.core.domain.model.Destination
import by.bashlikovvv.core.domain.model.Movie
import by.bashlikovvv.core.util.navigateToDestination
import by.bashlikovvv.morescreen.databinding.FragmentMoreBinding
import by.bashlikovvv.morescreen.di.MoreScreenComponentViewModel
import by.bashlikovvv.morescreen.presentation.ui.adapter.MoviesListAdapter
import by.bashlikovvv.morescreen.presentation.viewmodel.MoreFragmentViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.Lazy
import javax.inject.Inject

class MoreFragment : BottomSheetDialogFragment() {

    @StringRes private var categoryName: Int? = null

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
        ViewModelProvider(this)[MoreScreenComponentViewModel::class.java]
            .moreScreenComponent
            .inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryName = requireArguments().getInt(KEY_CATEGORY_NAME)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMoreBinding.inflate(inflater, container, false)

        setUpMoviesRecyclerView(binding)
        collectViewModelStates()

        return binding.root
    }

    private fun collectViewModelStates() {
        viewModel.navigationDestinationLiveEvent.observe(viewLifecycleOwner) {
            navigateToDestination(it)
        }
    }

    private fun setUpMoviesRecyclerView(binding: FragmentMoreBinding) {
        binding.moviesRecyclerView.adapter = adapter
    }

    companion object {

        const val KEY_CATEGORY_NAME: String = "key_category_name"

    }
}