package by.bashlikovvv.core.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel(), CoroutineScope {

    abstract val exceptionsHandler: CoroutineExceptionHandler

    inline fun BaseViewModel.launchIO(
        crossinline safeAction: suspend () -> Unit
    ): Job {
        return this.launch(exceptionsHandler + Dispatchers.IO) {
            safeAction.invoke()
        }
    }

    inline fun BaseViewModel.launchMain(
        crossinline safeAction: suspend () -> Unit
    ): Job {
        return this.launch(exceptionsHandler + Dispatchers.Main) {
            safeAction.invoke()
        }
    }

    inline fun BaseViewModel.launchDefault(
        crossinline safeAction: suspend () -> Unit
    ): Job {
        return this.launch(exceptionsHandler + Dispatchers.Default) {
            safeAction.invoke()
        }
    }

    inline fun BaseViewModel.launchUnconfined(
        crossinline safeAction: suspend () -> Unit,
    ): Job {
        return this.launch(exceptionsHandler + Dispatchers.Unconfined) {
            safeAction.invoke()
        }
    }

}