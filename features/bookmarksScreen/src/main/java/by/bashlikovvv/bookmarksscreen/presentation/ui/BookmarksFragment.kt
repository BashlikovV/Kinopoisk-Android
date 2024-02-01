package by.bashlikovvv.bookmarksscreen.presentation.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import by.bashlikovvv.bookmarksscreen.databinding.FragmentBookmarksBinding
import by.bashlikovvv.bookmarksscreen.di.BookmarksScreenComponentViewModel
import by.bashlikovvv.bookmarksscreen.presentation.ui.adapter.BookmarksListAdapter
import by.bashlikovvv.bookmarksscreen.presentation.viewmodel.BookmarksFragmentViewModel
import by.bashlikovvv.core.domain.model.Destination
import by.bashlikovvv.core.domain.model.Movie
import by.bashlikovvv.core.util.navigateToDestination
import dagger.Lazy
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class BookmarksFragment : Fragment() {

    @Inject internal lateinit var viewModelFactory: Lazy<BookmarksFragmentViewModel.Factory>

    private val viewModel: BookmarksFragmentViewModel by viewModels({ requireActivity() }) {
        viewModelFactory.get()
    }

    private val adapter = BookmarksListAdapter(
        onCLickListener = object : BookmarksListAdapter.BookmarksListAdapterClickListener {
            override fun notifyBookmarkClicked(movie: Movie) {
                viewModel.navigateToDestination(Destination.MovieDetailsScreen(movie.id))
            }

            override fun notifyDeleteBookmarkClicked(movie: Movie) {
                viewModel.removeBookmark(movie)
            }
        }
    )

    override fun onAttach(context: Context) {
        ViewModelProvider(this)[BookmarksScreenComponentViewModel::class.java]
            .bookmarksScreenComponent
            .inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentBookmarksBinding.inflate(inflater, container, false)

        setUpBookmarksRecyclerView(binding)
        collectViewModelStates()

        return binding.root
    }

    private fun setUpBookmarksRecyclerView(binding: FragmentBookmarksBinding) {
        binding.bookmarksRecyclerView.adapter = adapter
    }

    private fun collectViewModelStates() {
        lifecycleScope.launch {
            viewModel.navigationDestinationLiveEvent.observe(viewLifecycleOwner) { destination ->
                navigateToDestination(destination)
            }
        }
        lifecycleScope.launch {
            viewModel.bookmarksFlow.collectLatest { bookmarks ->
                adapter.submitList(bookmarks)
            }
        }
    }

}