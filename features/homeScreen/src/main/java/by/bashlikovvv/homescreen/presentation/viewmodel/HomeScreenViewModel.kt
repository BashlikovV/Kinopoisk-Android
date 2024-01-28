package by.bashlikovvv.homescreen.presentation.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import by.bashlikovvv.core.domain.usecase.GetMoviesByCollectionUseCase
import by.bashlikovvv.core.domain.usecase.GetMoviesByGenreUseCase
import by.bashlikovvv.core.domain.usecase.GetPagedMoviesUseCase
import by.bashlikovvv.core.domain.usecase.GetStringUseCase
import by.bashlikovvv.homescreen.R
import by.bashlikovvv.homescreen.domain.model.Category
import by.bashlikovvv.homescreen.domain.model.CategoryMore
import by.bashlikovvv.homescreen.domain.model.CategoryMovie
import by.bashlikovvv.homescreen.domain.model.CategoryText
import by.bashlikovvv.homescreen.domain.model.CategoryTitle
import by.bashlikovvv.homescreen.domain.model.MoviesCategory
import by.bashlikovvv.homescreen.presentation.view.HomeScreenFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

class HomeScreenViewModel(
    getPagedMoviesUseCase: GetPagedMoviesUseCase,
    private val getMoviesByGenreUseCase: GetMoviesByGenreUseCase,
    private val getMoviesByCollectionUseCase: GetMoviesByCollectionUseCase
) : ViewModel() {

    var moviesPagedData = getPagedMoviesUseCase.execute()
        private set

    private var _currentCategory = MutableStateFlow<Category>(defaultCategory)
    val currentCategory = _currentCategory.asStateFlow()

    private var _moviesData = MutableStateFlow<List<MoviesCategory>>(defaultMoviesCategory)
    val moviesData = _moviesData.asStateFlow()

    fun setCategory(category: Category) {
        _currentCategory.tryEmit(category)
    }

    fun makeMoviesData() = viewModelScope.launch(Dispatchers.IO) {
        _moviesData.tryEmit(listOf())
        HomeScreenFragment.collections.forEach { category ->
            _moviesData.update { it + listOf(CategoryTitle(category.itemText)) }
            _moviesData.update { movieCategories ->
                movieCategories + getMoviesByCollectionUseCase
                    .execute(getCollectionRequestNameByResId(category.itemText))
                    .map { movie -> CategoryMovie(movie) }
            }
            _moviesData.update { it + listOf(CategoryMore(category.itemText)) }
        }
        HomeScreenFragment.genres.forEach { category ->
            _moviesData.update { it + listOf(CategoryTitle(category.itemText)) }
            _moviesData.update { movieCategories ->
                movieCategories + getMoviesByGenreUseCase
                    .execute(getGenreRequestNameByResId(category.itemText))
                    .map { movie -> CategoryMovie(movie) }
            }
            _moviesData.update { it + listOf(CategoryMore(category.itemText)) }
        }
    }

    private fun getCollectionRequestNameByResId(@StringRes collection: Int) = when(collection) {
        R.string.top_250 -> "top250"
        R.string.top_500 -> "top500"
        R.string.most_popular -> "most-popular"
        R.string.for_deaf -> "for-deaf"
        else -> ""
    }

    private fun getGenreRequestNameByResId(@StringRes collection: Int) = when(collection) {
        R.string.cartoons -> "мультфильм"
        R.string.horrors -> "ужасы"
        R.string.fantasy -> "фэнтези"
        R.string.thrillers -> "триллер"
        R.string.action_movies -> "боевик"
        R.string.melodramas -> "мелодрама"
        R.string.detectives -> "детектив"
        R.string.adventures -> "приключения"
        R.string.military -> "военный"
        R.string.family -> "семейный"
        R.string.anime -> "аниме"
        R.string.historical -> "история"
        R.string.dramas -> "драма"
        else -> ""
    }

    class Factory @Inject constructor(
        private val getPagedMoviesUseCase: Provider<GetPagedMoviesUseCase>,
        private val getMoviesByGenreUseCase: Provider<GetMoviesByGenreUseCase>,
        private val getMoviesByCollectionUseCase: Provider<GetMoviesByCollectionUseCase>,
        private val getStringUseCase: Provider<GetStringUseCase>
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == HomeScreenViewModel::class.java)
            return HomeScreenViewModel(
                getPagedMoviesUseCase.get(),
                getMoviesByGenreUseCase.get(),
                getMoviesByCollectionUseCase.get()
            ) as T
        }

    }

    companion object {
        val defaultCategory = CategoryText(R.string.all)

        val defaultMoviesCategory = listOf(CategoryTitle(R.string.top_250))

    }

}