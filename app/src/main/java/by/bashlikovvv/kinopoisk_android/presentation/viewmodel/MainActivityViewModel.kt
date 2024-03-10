package by.bashlikovvv.kinopoisk_android.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import by.bashlikovvv.core.base.BaseViewModel
import by.bashlikovvv.core.domain.model.ParsedException
import by.bashlikovvv.core.domain.usecase.GetStringUseCase
import by.bashlikovvv.core.ext.toParsedException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider
import kotlin.coroutines.CoroutineContext

class MainActivityViewModel(
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

    private var _isReady = MutableStateFlow(false)
    val isReady = _isReady.asStateFlow()

    init {
        launchIO(
            safeAction = {
                delay(500)
                _isReady.tryEmit(true)
            },
            onError = { processException(it) }
        )
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
        private val getStringUseCase: Provider<GetStringUseCase>
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == MainActivityViewModel::class.java)
            return MainActivityViewModel(
                getStringUseCase.get()
            ) as T
        }

    }

}