package by.bashlikovvv.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class Destination : Parcelable {

    @Parcelize
    data object HomeScreen : Destination()

    @Parcelize
    data class MovieDetailsScreen(val movieId: Int) : Destination()

}