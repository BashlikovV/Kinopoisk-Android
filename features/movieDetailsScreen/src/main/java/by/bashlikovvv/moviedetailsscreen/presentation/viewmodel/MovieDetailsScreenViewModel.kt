package by.bashlikovvv.moviedetailsscreen.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.bashlikovvv.core.domain.model.Movie
import by.bashlikovvv.core.domain.model.ResultCommon
import by.bashlikovvv.core.domain.usecase.GetMovieByIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieDetailsScreenViewModel @Inject constructor(
    private val getMovieByIdUseCase: GetMovieByIdUseCase
) : ViewModel() {

    private var _movie = MutableStateFlow(Movie())
    val movie = _movie.asStateFlow()

    fun getMovieById(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        val result = getMovieByIdUseCase.execute(id)
        if (result is ResultCommon.Success<*>) {
            _movie.tryEmit(result.data as Movie)
        } else if (result is ResultCommon.Error) {
            _movie.tryEmit(Movie(description = result.cause?.message ?: ""))
        }
    }

}