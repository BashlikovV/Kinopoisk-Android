package by.bashlikovvv.morescreen.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import by.bashlikovvv.core.base.SingleLiveEvent
import by.bashlikovvv.core.domain.model.Destination
import by.bashlikovvv.core.domain.model.Movie
import by.bashlikovvv.core.domain.usecase.GetPagedMoviesByGenreUseCase
import by.bashlikovvv.core.domain.usecase.GetStringUseCase
import by.bashlikovvv.morescreen.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

class MoreFragmentViewModel(
    private val getStringUseCase: GetStringUseCase,
    private val getPagedMoviesByGenreUseCase: GetPagedMoviesByGenreUseCase
) : ViewModel() {

    private var _movies: Flow<PagingData<Movie>>? = null
    val movies get() = _movies ?: flowOf(getEmptyMoviesPagingData())

    private var _navigationDestinationLiveEvent = SingleLiveEvent<Destination>()
    val navigationDestinationLiveEvent: LiveData<Destination> = _navigationDestinationLiveEvent

    fun navigateToDestination(destination: Destination) {
        _navigationDestinationLiveEvent.postValue(destination)
    }

    fun updateMoviesState(genre: String) = viewModelScope.launch(Dispatchers.IO) {
        _movies = getPagedMoviesByGenreUseCase.execute(genre)
        delay(250)
    }

    private fun getEmptyMoviesPagingData(): PagingData<Movie> {
        return PagingData
            .from(
                listOf(
                    Movie(
                        name = getStringUseCase.execute(R.string.something_went_wrong)
                    )
                )
            )
    }

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