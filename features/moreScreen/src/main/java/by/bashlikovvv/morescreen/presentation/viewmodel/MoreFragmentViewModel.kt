package by.bashlikovvv.morescreen.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagingData
import by.bashlikovvv.core.base.SingleLiveEvent
import by.bashlikovvv.core.domain.model.Destination
import by.bashlikovvv.core.domain.model.Movie
import by.bashlikovvv.core.domain.usecase.GetPagedMoviesByGenreAndNameUseCase
import by.bashlikovvv.core.domain.usecase.GetPagedMoviesByGenreUseCase
import by.bashlikovvv.core.domain.usecase.GetStringUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject
import javax.inject.Provider

@OptIn(FlowPreview::class)
class MoreFragmentViewModel(
    private val getStringUseCase: GetStringUseCase,
    private val getPagedMoviesByGenreUseCase: GetPagedMoviesByGenreUseCase,
    private val getPagedMoviesByGenreAndNameUseCase: GetPagedMoviesByGenreAndNameUseCase
) : ViewModel() {

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

    class Factory @Inject constructor(
        private val getStringUseCase: Provider<GetStringUseCase>,
        private val getPagedMoviesByGenreUseCase: Provider<GetPagedMoviesByGenreUseCase>,
        private val getPagedMoviesByGenreAndNameUseCase: Provider<GetPagedMoviesByGenreAndNameUseCase>
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == MoreFragmentViewModel::class.java)
            return MoreFragmentViewModel(
                getStringUseCase.get(),
                getPagedMoviesByGenreUseCase.get(),
                getPagedMoviesByGenreAndNameUseCase.get()
            ) as T
        }

    }

}