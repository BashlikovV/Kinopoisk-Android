package by.bashlikovvv.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Long = 0,
    val name: String = "",
    val description: String = "",
    val poster: String = "",
    val age: Int = 0,
    val genres: List<String> = listOf(),
    val page: Int = 0,
    val collections: List<String> = listOf(),
    val isBookmark: Boolean = false
) : Parcelable