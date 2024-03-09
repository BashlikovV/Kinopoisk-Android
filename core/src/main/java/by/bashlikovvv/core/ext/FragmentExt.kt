@file:Suppress("UNUSED")
package by.bashlikovvv.core.ext

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.EmptyCoroutineContext

inline fun Fragment.launchIO(
    crossinline safeAction: suspend () -> Unit,
    exceptionHandler: CoroutineExceptionHandler
): Job {
    return lifecycleScope.launch(exceptionHandler + Dispatchers.IO) {
        safeAction.invoke()
    }
}

inline fun Fragment.launchMain(
    crossinline safeAction: suspend () -> Unit,
    exceptionHandler: CoroutineExceptionHandler
): Job {
    return lifecycleScope.launch(exceptionHandler + Dispatchers.Main) {
        safeAction.invoke()
    }
}

inline fun Fragment.launchMain(
    crossinline safeAction: suspend () -> Unit,
    crossinline onError: (Throwable) -> Unit
): Job {
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError.invoke(throwable)
    }

    return lifecycleScope.launch(exceptionHandler + Dispatchers.Main) {
        safeAction.invoke()
    }
}

inline fun Fragment.launchDefault(
    crossinline safeAction: suspend () -> Unit,
    exceptionHandler: CoroutineExceptionHandler
): Job {
    return lifecycleScope.launch(exceptionHandler + Dispatchers.Default) {
        safeAction.invoke()
    }
}

inline fun Fragment.launchUnconfined(
    crossinline safeAction: suspend () -> Unit,
    exceptionHandler: CoroutineExceptionHandler
): Job {
    return lifecycleScope.launch(exceptionHandler + Dispatchers.Unconfined) {
        safeAction.invoke()
    }
}

inline fun Fragment.launchEmpty(
    crossinline safeAction: suspend () -> Unit,
    exceptionHandler: CoroutineExceptionHandler
): Job {
    return lifecycleScope.launch(exceptionHandler + EmptyCoroutineContext) {
        safeAction.invoke()
    }
}