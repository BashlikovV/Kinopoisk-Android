package by.bashlikovvv.bookmarksscreen.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import by.bashlikovvv.core.base.SingleLiveEvent
import by.bashlikovvv.core.domain.model.Destination
import by.bashlikovvv.core.domain.model.Movie
import by.bashlikovvv.core.domain.usecase.GetBookmarksUseCase
import by.bashlikovvv.core.domain.usecase.RemoveBookmarkUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

class BookmarksFragmentViewModel(
    private val getBookmarksUseCase: GetBookmarksUseCase,
    private val removeBookmarkUseCase: RemoveBookmarkUseCase
) : ViewModel() {

    private var _isUpdating = MutableStateFlow(true)
    val isUpdating = _isUpdating.asStateFlow()

    private var _bookmarksFlow = MutableStateFlow(listOf<Movie>())
    val bookmarksFlow = _bookmarksFlow.asStateFlow()

    private var _navigationDestinationLiveEvent = SingleLiveEvent<Destination>()
    val navigationDestinationLiveEvent: LiveData<Destination> = _navigationDestinationLiveEvent

    init {
        loadBookmarks()
    }

    fun navigateToDestination(destination: Destination) {
        _navigationDestinationLiveEvent.postValue(destination)
    }

    fun removeBookmark(movie: Movie) = viewModelScope.launch(Dispatchers.IO) {
        if (removeBookmarkUseCase.execute(movie)) {
            _bookmarksFlow.update { list ->
                list.filter { it != movie }
            }
        }
    }

    fun loadBookmarks() = viewModelScope.launch(Dispatchers.IO) {
        _isUpdating.tryEmit(true)
        _bookmarksFlow.tryEmit(getBookmarksUseCase.execute())
        _isUpdating.tryEmit(false)
    }

    class Factory @Inject constructor(
        private val getBookmarksUseCase: Provider<GetBookmarksUseCase>,
        private val removeBookmarkUseCase: Provider<RemoveBookmarkUseCase>
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == BookmarksFragmentViewModel::class.java)
            return BookmarksFragmentViewModel(
                getBookmarksUseCase.get(),
                removeBookmarkUseCase.get()
            ) as T
        }

    }

}