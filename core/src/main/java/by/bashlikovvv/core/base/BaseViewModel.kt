package by.bashlikovvv.core.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Suppress("UNUSED")
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

    /* with custom error processing */

    inline fun launchIO(
        crossinline safeAction: suspend () -> Unit,
        crossinline onError: (Throwable) -> Unit,
        errorDispatcher: CoroutineDispatcher = Dispatchers.Main
    ): Job {
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            this.launch(errorDispatcher) {
                onError.invoke(throwable)
            }
        }

        return this.launch(exceptionHandler + Dispatchers.IO) {
            safeAction.invoke()
        }
    }

    inline fun launchMain(
        crossinline safeAction: suspend () -> Unit,
        crossinline onError: (Throwable) -> Unit
    ): Job {
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            this.launch(Dispatchers.Main) {
                onError.invoke(throwable)
            }
        }

        return this.launch(exceptionHandler + Dispatchers.Main) {
            safeAction.invoke()
        }
    }

    inline fun launchDefault(
        crossinline safeAction: suspend () -> Unit,
        crossinline onError: (Throwable) -> Unit,
        errorDispatcher: CoroutineDispatcher = Dispatchers.Main
    ): Job {
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            this.launch(errorDispatcher) {
                onError.invoke(throwable)
            }
        }

        return this.launch(exceptionHandler + Dispatchers.Default) {
            safeAction.invoke()
        }
    }

    inline fun launchUnconfined(
        crossinline safeAction: suspend () -> Unit,
        crossinline onError: (Throwable) -> Unit,
        errorDispatcher: CoroutineDispatcher = Dispatchers.Main
    ): Job {
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            this.launch(errorDispatcher) {
                onError.invoke(throwable)
            }
        }

        return this.launch(exceptionHandler + Dispatchers.Unconfined) {
            safeAction.invoke()
        }
    }


}