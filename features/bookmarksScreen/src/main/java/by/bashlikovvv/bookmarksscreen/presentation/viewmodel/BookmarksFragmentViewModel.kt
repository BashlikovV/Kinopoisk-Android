package by.bashlikovvv.bookmarksscreen.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import by.bashlikovvv.bookmarksscreen.presentation.domain.LocalChanges
import by.bashlikovvv.core.base.BaseViewModel
import by.bashlikovvv.core.base.SingleLiveEvent
import by.bashlikovvv.core.domain.model.Destination
import by.bashlikovvv.core.domain.model.Movie
import by.bashlikovvv.core.domain.model.ParsedException
import by.bashlikovvv.core.domain.usecase.GetBookmarksByNameUseCase
import by.bashlikovvv.core.domain.usecase.GetBookmarksUseCase
import by.bashlikovvv.core.domain.usecase.GetStringUseCase
import by.bashlikovvv.core.domain.usecase.RemoveBookmarkUseCase
import by.bashlikovvv.core.ext.toParsedException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider
import kotlin.coroutines.CoroutineContext

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class BookmarksFragmentViewModel @Inject constructor(
    private val getBookmarksUseCase: GetBookmarksUseCase,
    private val removeBookmarkUseCase: RemoveBookmarkUseCase,
    private val getBookmarksByNameUseCase: GetBookmarksByNameUseCase,
    private val getStringUseCase: GetStringUseCase
) : BaseViewModel() {

    override val coroutineContext: CoroutineContext = viewModelScope.coroutineContext

    val exceptionsHandler = CoroutineExceptionHandler { _, throwable ->
        processException(throwable)
    }

    private val _exceptionsFlow = MutableSharedFlow<ParsedException>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val exceptionsFlow = _exceptionsFlow.asSharedFlow()

    private var _isUpdating = MutableStateFlow(true)
    val isUpdating = _isUpdating.asStateFlow()

    private val _searchBy = MutableStateFlow("")

    private val localChanges = LocalChanges()
    private val localChangesFlow = MutableStateFlow(localChanges)

    private var _bookmarksFlow: Flow<List<Movie>>
    val bookmarksFlow get() = _bookmarksFlow

    private var _navigationDestinationLiveEvent = SingleLiveEvent<Destination>()
    val navigationDestinationLiveEvent: LiveData<Destination> = _navigationDestinationLiveEvent

    init {
        loadBookmarks()
        val originFlow = _searchBy.asStateFlow()
            .debounce(500)
            .flatMapLatest { searchBy ->
                if (searchBy.isEmpty()) {
                    flowOf(getBookmarksUseCase.execute())
                } else {
                    flowOf(getBookmarksByNameUseCase.execute(searchBy))
                }
            }

        _bookmarksFlow = combine(
            originFlow,
            localChangesFlow,
            this::merge
        )
    }

    fun navigateToDestination(destination: Destination) {
        _navigationDestinationLiveEvent.postValue(destination)
    }

    fun removeBookmark(movie: Movie) = launchIO(
        safeAction = {
            if (removeBookmarkUseCase.execute(movie)) {
                _bookmarksFlow.transform { list ->
                    emit(list.filter { it != movie })
                }
            }
        },
        onError = { processException(it) }
    )

    fun loadBookmarks() = launchIO(
        safeAction = {
            _isUpdating.tryEmit(true)
            _bookmarksFlow.transform {
                emit(getBookmarksUseCase.execute())
            }
            _isUpdating.tryEmit(false)
        },
        onError = { processException(it) }
    )

    fun onSearch(searchBy: String) {
        _searchBy.tryEmit(searchBy)
    }

    private fun merge(bookmarks: List<Movie>, localChanges: LocalChanges): List<Movie> {
        if (bookmarks.isEmpty()) return bookmarks
        _isUpdating.tryEmit(true)

        return bookmarks.map { bookmark ->
            val localSelectedFlag = localChanges.isSelected(bookmark.id)
            val localInProgressFlag = localChanges.isInProgress(bookmark.id)

            val bookmarkWithLocalChanges = if (localSelectedFlag == null) {
                if (localInProgressFlag) {
                    bookmark.copy()
                } else {
                    bookmark
                }
            } else {
                bookmark.copy()
            }
            _isUpdating.tryEmit(false)

            bookmarkWithLocalChanges
        }
    }

    private fun processException(throwable: Throwable) {
        launch(Dispatchers.Main) {
            _exceptionsFlow.tryEmit(
                throwable.toParsedException(::titleBuilder)
            )
        }
    }

    private fun titleBuilder(throwable: Throwable): String {
        return throwable.localizedMessage ?: getStringUseCase
            .execute(by.bashlikovvv.core.R.string.smth_went_wrong)
    }

    class Factory @Inject constructor(
        private val getBookmarksUseCase: Provider<GetBookmarksUseCase>,
        private val removeBookmarkUseCase: Provider<RemoveBookmarkUseCase>,
        private val getBookmarksByNameUseCase: Provider<GetBookmarksByNameUseCase>,
        private val getStringUseCase: Provider<GetStringUseCase>
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == BookmarksFragmentViewModel::class.java)
            return BookmarksFragmentViewModel(
                getBookmarksUseCase.get(),
                removeBookmarkUseCase.get(),
                getBookmarksByNameUseCase.get(),
                getStringUseCase.get()
            ) as T
        }

    }

}