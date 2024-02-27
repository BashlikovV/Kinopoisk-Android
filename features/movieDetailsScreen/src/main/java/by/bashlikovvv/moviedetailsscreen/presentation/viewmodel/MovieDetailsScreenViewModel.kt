package by.bashlikovvv.moviedetailsscreen.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.bashlikovvv.core.domain.model.MovieDetails
import by.bashlikovvv.core.domain.model.ResultCommon
import by.bashlikovvv.core.domain.usecase.GetMovieDetailsByIdUseCase
import by.bashlikovvv.core.domain.usecase.GetStringUseCase
import by.bashlikovvv.moviedetailsscreen.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieDetailsScreenViewModel @Inject constructor(
    private val getMovieDetailsByIdUseCase: GetMovieDetailsByIdUseCase,
    private val getStringUseCase: GetStringUseCase
) : ViewModel() {

    private var _movie = MutableStateFlow(MovieDetails())
    val movie = _movie.asStateFlow()

    private var _exceptions = MutableLiveData<String>()
    val exceptions: LiveData<String> = _exceptions

    fun getMovieById(id: Long) = viewModelScope.launch(Dispatchers.IO) {
        val result = getMovieDetailsByIdUseCase.execute(id)
        if (result is ResultCommon.Success<*>) {
            _movie.tryEmit(result.data as MovieDetails)
        } else {
            withContext(Dispatchers.Main) {
                _exceptions.value = getStringUseCase.execute(R.string.something_went_wrong)
            }
        }
    }

}