package by.bashlikovvv.bookmarksscreen.presentation.ui

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import by.bashlikovvv.bookmarksscreen.R
import by.bashlikovvv.bookmarksscreen.databinding.FragmentBookmarksBinding
import by.bashlikovvv.bookmarksscreen.di.BookmarksScreenComponentProvider
import by.bashlikovvv.bookmarksscreen.presentation.ui.adapter.BookmarksListAdapter
import by.bashlikovvv.bookmarksscreen.presentation.viewmodel.BookmarksFragmentViewModel
import by.bashlikovvv.core.base.BaseFragment
import by.bashlikovvv.core.domain.model.FlowDestinations
import by.bashlikovvv.core.domain.model.Movie
import by.bashlikovvv.core.ext.launchMain
import by.bashlikovvv.core.ext.navigateToFlow
import dagger.Lazy
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import javax.inject.Inject

class BookmarksFragment : BaseFragment<FragmentBookmarksBinding>() {

    @Inject internal lateinit var viewModelFactory: Lazy<BookmarksFragmentViewModel.Factory>

    private val viewModel: BookmarksFragmentViewModel by viewModels({ requireActivity() }) {
        viewModelFactory.get()
    }

    private val adapter = BookmarksListAdapter(
        onCLickListener = object : BookmarksListAdapter.BookmarksListAdapterClickListener {
            override fun notifyBookmarkClicked(movie: Movie) {
                viewModel.navigateToFlow(FlowDestinations.DetailsScreen(movie.id))
            }

            override fun notifyDeleteBookmarkClicked(movie: Movie) {
                viewModel.removeBookmark(movie)
            }
        }
    )

    override fun onAttach(context: Context) {
        (requireContext().applicationContext as BookmarksScreenComponentProvider)
            .provideBookmarksScreenComponent()
            .inject(this)
        super.onAttach(context)
    }

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentBookmarksBinding {
        return FragmentBookmarksBinding.inflate(inflater, container, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        viewModel.loadBookmarks()
        setUpBookmarksRecyclerView()
        setUpSwipeRefreshLayout()
        collectViewModelStates()
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

    private fun setUpBookmarksRecyclerView() {
        binding.bookmarksRecyclerView.adapter = adapter
    }

    private fun setUpSwipeRefreshLayout() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadBookmarks().invokeOnCompletion {
                binding.swipeRefreshLayout.isRefreshing = false
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun collectViewModelStates() {
        launchMain(
            safeAction = {
                viewModel.bookmarksFlow.collectLatest { bookmarks ->
                    if (bookmarks.isEmpty()) {
                        AlertDialog.Builder(requireContext())
                            .setTitle(R.string.you_do_not_have_bookmarks)
                            .setMessage(R.string.add_bookmarks)
                            .show()
                    } else {
                        adapter.submitList(bookmarks)
                    }
                }
            },
            exceptionHandler = viewModel.exceptionsHandler
        )
        viewModel.navigationFlowLiveEventDestinations.observe(viewLifecycleOwner) { destination ->
            navigateToFlow(destination)
        }
        launchMain(
            safeAction = {
                viewModel.isUpdating
                    .debounce(500)
                    .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                    .collectLatest {
                        if (it) {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.bookmarksRecyclerView.visibility = View.GONE
                        } else {
                            binding.progressBar.visibility = View.GONE
                            binding.bookmarksRecyclerView.visibility = View.VISIBLE
                        }
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

}