package by.bashlikovvv.core.domain.usecase

import android.app.Application
import androidx.annotation.StringRes

class GetStringUseCase(private val context: Application) {

    fun execute(@StringRes resId: Int): String = context.getString(resId)

}