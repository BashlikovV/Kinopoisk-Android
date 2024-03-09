package by.bashlikovvv.moviedetailsscreen.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import by.bashlikovvv.core.base.BaseViewModel
import by.bashlikovvv.core.domain.model.MovieDetails
import by.bashlikovvv.core.domain.model.ResultCommon
import by.bashlikovvv.core.domain.usecase.GetMovieDetailsByIdUseCase
import by.bashlikovvv.core.domain.usecase.GetStringUseCase
import by.bashlikovvv.moviedetailsscreen.R
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
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

    private val job: Job = SupervisorJob()

    override val coroutineContext: CoroutineContext = job

    override val exceptionsHandler = CoroutineExceptionHandler { _, throwable ->
        launch(Dispatchers.Main) { _exceptionsFlow.tryEmit(throwable) }
    }

    private val _exceptionsFlow = MutableSharedFlow<Throwable>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val exceptionsFlow = _exceptionsFlow.asSharedFlow()

    private var _movie = MutableStateFlow(MovieDetails())
    val movie = _movie.asStateFlow()

    private var _exceptions = MutableLiveData<String>()
    val exceptions: LiveData<String> = _exceptions

    fun getMovieById(id: Long) = launchIO {
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