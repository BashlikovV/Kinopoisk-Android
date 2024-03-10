package by.bashlikovvv.moviedetailsscreen.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import by.bashlikovvv.core.base.BaseViewModel
import by.bashlikovvv.core.domain.model.MovieDetails
import by.bashlikovvv.core.domain.model.ParsedException
import by.bashlikovvv.core.domain.model.ResultCommon
import by.bashlikovvv.core.domain.usecase.GetMovieDetailsByIdUseCase
import by.bashlikovvv.core.domain.usecase.GetStringUseCase
import by.bashlikovvv.core.ext.toParsedException
import by.bashlikovvv.moviedetailsscreen.R
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class MovieDetailsScreenViewModel @Inject constructor(
    private val getMovieDetailsByIdUseCase: GetMovieDetailsByIdUseCase,
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

    private var _movie = MutableStateFlow(MovieDetails())
    val movie = _movie.asStateFlow()

    private var _exceptions = MutableLiveData<String>()
    val exceptions: LiveData<String> = _exceptions

    fun getMovieById(id: Long) = launchIO(
        safeAction = {
            val result = getMovieDetailsByIdUseCase.execute(id)
            if (result is ResultCommon.Success<*>) {
                _movie.tryEmit(result.data as MovieDetails)
            } else {
                withContext(Dispatchers.Main) {
                    _exceptions.value = getStringUseCase.execute(R.string.something_went_wrong)
                }
            }
        },
        onError = { processException(it) }
    )

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

}