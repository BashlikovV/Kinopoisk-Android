package by.bashlikovvv.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class MovieDetails(
    val id: Long = 0,
    val year: Int? = null,
    val rating: Rating? = null,
    val videos: List<String>? = null,
    val type: String? = null,
    val logo: String? = null,
    val top250: Boolean? = null,
    val budget: Budget? = null,
    val similarMovies: List<Movie>? = null,
    val shortDescription: String = "",
    val persons: List<Person>? = null,
    val name: String = "",
    val poster: String = "",
    val facts: List<String>? = null,
    val movieLength: Int? = null,
    val genres: List<String> = listOf(),
    val watchAbility: List<String> = listOf()
)