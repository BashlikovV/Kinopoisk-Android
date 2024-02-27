package by.bashlikovvv.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Rating(
    val imdb: Double = 0.0,
    val kp: Double = 0.0,
    val await: String? = null,
    val russianFilmCritics: Double = 0.0,
    val filmCritics: Double = 0.0
) : Parcelable