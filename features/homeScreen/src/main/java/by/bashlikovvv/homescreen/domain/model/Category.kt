package by.bashlikovvv.homescreen.domain.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import by.bashlikovvv.core.domain.model.Movie

sealed class Category

data class CategoryLogo(@DrawableRes val imageId: Int) : Category()

data class CategoryText(@StringRes val itemText: Int) : Category()