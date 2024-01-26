package by.bashlikovvv.homescreen.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import by.bashlikovvv.core.domain.usecase.GetPagedMoviesUseCase
import by.bashlikovvv.homescreen.R
import by.bashlikovvv.homescreen.domain.model.Category
import by.bashlikovvv.homescreen.domain.model.CategoryText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Provider

class HomeScreenViewModel(
    getPagedMoviesUseCase: GetPagedMoviesUseCase
) : ViewModel() {

    var moviesPagedData = getPagedMoviesUseCase.execute()
        private set

    private var _currentCategory = MutableStateFlow<Category>(defaultCategory)
    val currentCategory = _currentCategory.asStateFlow()

    fun setCategory(category: Category) {
        _currentCategory.tryEmit(category)
    }

    class Factory @Inject constructor(
        private val getPagedMoviesUseCase: Provider<GetPagedMoviesUseCase>
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == HomeScreenViewModel::class.java)
            return HomeScreenViewModel(
                getPagedMoviesUseCase.get()
            ) as T
        }

    }

    companion object {
        val  defaultCategory = CategoryText(R.string.all)
    }

}