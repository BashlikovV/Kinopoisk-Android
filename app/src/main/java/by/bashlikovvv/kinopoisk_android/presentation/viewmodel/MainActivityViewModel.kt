package by.bashlikovvv.kinopoisk_android.presentation.viewmodel

import by.bashlikovvv.core.base.BaseViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class MainActivityViewModel @Inject constructor () : BaseViewModel() {

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

    private var _isReady = MutableStateFlow(false)
    val isReady = _isReady.asStateFlow()

    init {
        launchIO(
            safeAction = {
                delay(500)
                _isReady.tryEmit(true)
            }
        )
    }

}