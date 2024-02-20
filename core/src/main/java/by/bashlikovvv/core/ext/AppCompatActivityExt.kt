package by.bashlikovvv.core.ext

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun AppCompatActivity.launchOnLifecycle(
    context: CoroutineContext = EmptyCoroutineContext,
    repeatOn: Lifecycle.State,
    function: suspend () -> Unit
): Job {
    return lifecycleScope.launch(context) {
        repeatOnLifecycle(repeatOn) {
            function()
        }
    }
}