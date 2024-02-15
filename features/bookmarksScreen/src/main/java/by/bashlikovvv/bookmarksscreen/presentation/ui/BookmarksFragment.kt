package by.bashlikovvv.bookmarksscreen.presentation.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
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
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

class BookmarksFragment : Fragment() {

//    @Inject internal lateinit var viewModelFactory: Lazy<BookmarksFragmentViewModel.Factory>
//
//    private val viewModel: BookmarksFragmentViewModel by viewModels({ requireActivity() }) {
//        viewModelFactory.get()
//    }

    private val viewModel: BookmarksFragmentViewModel by viewModels({ requireActivity() })

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

        viewModel.loadBookmarks()
        setUpBookmarksRecyclerView(binding)
        setUpSwipeRefreshLayout(binding)
        collectViewModelStates(binding)
        setUpSearchView(binding.searchView)


        return binding.root
    }

    private fun setUpSearchView(menuItem: SearchView) {
        with(menuItem) {
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
            setOnSearchClickListener {
                viewModel.onSearch("")
            }
        }
    }

    private fun setUpBookmarksRecyclerView(binding: FragmentBookmarksBinding) {
        binding.bookmarksRecyclerView.adapter = adapter
    }

    private fun setUpSwipeRefreshLayout(binding: FragmentBookmarksBinding) {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadBookmarks().invokeOnCompletion {
                binding.swipeRefreshLayout.isRefreshing = false
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun collectViewModelStates(binding: FragmentBookmarksBinding) {
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
        lifecycleScope.launch {
            viewModel.isUpdating
                .debounce(500)
                .collectLatest {
                if (it) {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.bookmarksRecyclerView.visibility = View.GONE
                } else {
                    binding.progressBar.visibility = View.GONE
                    binding.bookmarksRecyclerView.visibility = View.VISIBLE
                }
            }
        }
    }

}