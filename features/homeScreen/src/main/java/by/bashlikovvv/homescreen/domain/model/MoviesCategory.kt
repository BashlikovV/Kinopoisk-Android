package by.bashlikovvv.homescreen.domain.model

import androidx.annotation.StringRes
import by.bashlikovvv.core.domain.model.Movie

sealed class MoviesCategory

data class CategoryTitle(@StringRes val itemText: Int) : MoviesCategory()

data class CategoryMovie(val movie: Movie): MoviesCategory()

data class CategoryMore(@StringRes val categoryName: Int): MoviesCategory()