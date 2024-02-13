package by.bashlikovvv.core.domain.model

import android.os.Parcelable
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize

sealed class Destination : Parcelable {

    @Parcelize
    data object HomeScreen : Destination()

    @Parcelize
    data class MovieDetailsScreen(val movieId: Long) : Destination()

    @Parcelize
    data class MoreScreen(val categoryName: String) : Destination()

}