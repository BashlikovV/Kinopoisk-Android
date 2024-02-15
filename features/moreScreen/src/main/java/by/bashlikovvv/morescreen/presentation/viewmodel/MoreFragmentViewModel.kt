package by.bashlikovvv.morescreen.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagingData
import by.bashlikovvv.core.base.SingleLiveEvent
import by.bashlikovvv.core.domain.model.Destination
import by.bashlikovvv.core.domain.model.Movie
import by.bashlikovvv.core.domain.usecase.GetPagedMoviesByGenreUseCase
import by.bashlikovvv.core.domain.usecase.GetStringUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Provider

class MoreFragmentViewModel(
    private val getStringUseCase: GetStringUseCase,
    private val getPagedMoviesByGenreUseCase: GetPagedMoviesByGenreUseCase
) : ViewModel() {

    private var _navigationDestinationLiveEvent = SingleLiveEvent<Destination>()
    val navigationDestinationLiveEvent: LiveData<Destination> = _navigationDestinationLiveEvent

    fun navigateToDestination(destination: Destination) {
        _navigationDestinationLiveEvent.postValue(destination)
    }

    fun getMovies(genre: String): Flow<PagingData<Movie>> =
        getPagedMoviesByGenreUseCase.execute(genre)

    class Factory @Inject constructor(
        private val getStringUseCase: Provider<GetStringUseCase>,
        private val getPagedMoviesByGenreUseCase: Provider<GetPagedMoviesByGenreUseCase>
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == MoreFragmentViewModel::class.java)
            return MoreFragmentViewModel(
                getStringUseCase.get(),
                getPagedMoviesByGenreUseCase.get()
            ) as T
        }

    }

}