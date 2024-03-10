package by.bashlikovvv.morescreen.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import by.bashlikovvv.core.base.BaseViewModel
import by.bashlikovvv.core.base.SingleLiveEvent
import by.bashlikovvv.core.domain.model.Destination
import by.bashlikovvv.core.domain.model.Movie
import by.bashlikovvv.core.domain.model.ParsedException
import by.bashlikovvv.core.domain.usecase.GetPagedMoviesByGenreAndNameUseCase
import by.bashlikovvv.core.domain.usecase.GetPagedMoviesByGenreUseCase
import by.bashlikovvv.core.domain.usecase.GetStringUseCase
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
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider
import kotlin.coroutines.CoroutineContext

@OptIn(FlowPreview::class)
class MoreFragmentViewModel(
    private val getPagedMoviesByGenreUseCase: GetPagedMoviesByGenreUseCase,
    private val getPagedMoviesByGenreAndNameUseCase: GetPagedMoviesByGenreAndNameUseCase,
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

    private var _navigationDestinationLiveEvent = SingleLiveEvent<Destination>()
    val navigationDestinationLiveEvent: LiveData<Destination> = _navigationDestinationLiveEvent

    private var _searchBy = MutableStateFlow("")

    fun navigateToDestination(destination: Destination) {
        _navigationDestinationLiveEvent.postValue(destination)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getMovies(genre: String): Flow<PagingData<Movie>> {
        return _searchBy
            .debounce(500)
            .flatMapLatest { searchBy ->
                if (searchBy.isEmpty()) {
                    getPagedMoviesByGenreUseCase.execute(genre)
                } else {
                    getPagedMoviesByGenreAndNameUseCase.execute(genre, searchBy)
                }
            }
    }

    fun onSearch(searchBy: String) {
        _searchBy.tryEmit(searchBy)
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
        private val getPagedMoviesByGenreUseCase: Provider<GetPagedMoviesByGenreUseCase>,
        private val getPagedMoviesByGenreAndNameUseCase: Provider<GetPagedMoviesByGenreAndNameUseCase>,
        private val getStringUseCase: Provider<GetStringUseCase>
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == MoreFragmentViewModel::class.java)
            return MoreFragmentViewModel(
                getPagedMoviesByGenreUseCase.get(),
                getPagedMoviesByGenreAndNameUseCase.get(),
                getStringUseCase.get()
            ) as T
        }

    }

}