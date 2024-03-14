package by.bashlikovvv.homescreen.presentation.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import by.bashlikovvv.core.base.BaseViewModel
import by.bashlikovvv.core.base.SingleLiveEvent
import by.bashlikovvv.core.domain.model.Destination
import by.bashlikovvv.core.domain.model.HomeFlowDestinations
import by.bashlikovvv.core.domain.model.Movie
import by.bashlikovvv.core.domain.model.ParsedException
import by.bashlikovvv.core.domain.usecase.AddBookmarkUseCase
import by.bashlikovvv.core.domain.usecase.GetMoviesByCollectionUseCase
import by.bashlikovvv.core.domain.usecase.GetMoviesByGenreUseCase
import by.bashlikovvv.core.domain.usecase.GetPagedMoviesUseCase
import by.bashlikovvv.core.domain.usecase.GetStringUseCase
import by.bashlikovvv.core.domain.usecase.RemoveBookmarkUseCase
import by.bashlikovvv.core.ext.toParsedException
import by.bashlikovvv.homescreen.R
import by.bashlikovvv.homescreen.domain.model.Category
import by.bashlikovvv.homescreen.domain.model.CategoryMore
import by.bashlikovvv.homescreen.domain.model.CategoryMovie
import by.bashlikovvv.homescreen.domain.model.CategoryText
import by.bashlikovvv.homescreen.domain.model.CategoryTitle
import by.bashlikovvv.homescreen.domain.model.MoviesCategory
import by.bashlikovvv.homescreen.presentation.ui.HomeScreenFragment
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider
import kotlin.coroutines.CoroutineContext

class HomeScreenViewModel(
    private val getPagedMoviesUseCase: GetPagedMoviesUseCase,
    private val getMoviesByGenreUseCase: GetMoviesByGenreUseCase,
    private val getMoviesByCollectionUseCase: GetMoviesByCollectionUseCase,
    private val addBookmarkUseCase: AddBookmarkUseCase,
    private val removeBookmarkUseCase: RemoveBookmarkUseCase,
    private val getStringUseCase: GetStringUseCase
) : BaseViewModel() {

    override val coroutineContext: CoroutineContext = viewModelScope.coroutineContext

    val exceptionsHandler = CoroutineExceptionHandler { _, throwable ->
        processException(throwable)
    }

    private val _exceptionsFlow = MutableSharedFlow<ParsedException>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val exceptionsFlow = _exceptionsFlow.asSharedFlow()

    // AllMoviesFragment progress bar state
    private var _allMoviesUpdateState = MutableStateFlow(false)
    val allMoviesUpdateState = _allMoviesUpdateState.asStateFlow()

    // MoviesFragment progress bar state
    private var _moviesUpdateState = MutableStateFlow(false)
    val moviesUpdateState = _moviesUpdateState.asStateFlow()

    // Paged data with all movies for AllMoviesFragment
    var moviesPagedData = getPagedMoviesUseCase.execute()
        private set

    // Flow with current visible category at MoviesFragment
    private var _currentCategory = MutableStateFlow<Category>(defaultCategory)
    val currentCategory = _currentCategory.asStateFlow()

    // Flow with current selected category as HomeScreenFragment
    private var _moviesCurrentCategory = MutableStateFlow<Category>(defaultCategory)
    val moviesCurrentCategory = _moviesCurrentCategory.asStateFlow()

    // Flow with mapped by categories movies for MoviesFragment
    private var _moviesData = MutableStateFlow<List<MoviesCategory>>(defaultMoviesCategory)
    val moviesData = _moviesData.asStateFlow()

    // LiveData with Category to navigate between AllMoviesFragment and MoviesFragment
    private var _navigateToCategoryLiveEvent = SingleLiveEvent<Category>()
    val navigateToCategoryLiveEvent: LiveData<Category> = _navigateToCategoryLiveEvent

    private var _navigationFlowLiveEvent = SingleLiveEvent<Destination>()
    val navigationFlowLiveEvent: LiveData<Destination> = _navigationFlowLiveEvent

    private var _navigateToLiveEvent = SingleLiveEvent<HomeFlowDestinations>()
    val navigateToLiveEvent: LiveData<HomeFlowDestinations> = _navigateToLiveEvent

    init {
        makeMoviesData()
    }

    // Setting the current visible category for MoviesRecyclerView to the MoviesFragment
    fun setCategory(category: Category) {
        _currentCategory.tryEmit(category)
    }

    // Setting the current selected category for the CategoriesListAdapter to the HomeScreenFragment
    fun setMoviesCurrentCategory(category: MoviesCategory) {
        val categoryText = CategoryText((category as CategoryTitle).itemText)
        _moviesCurrentCategory.tryEmit(categoryText)
    }

    private fun makeMoviesData() = launchIO(
        safeAction = {
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
        },
        onError = { processException(it) }
    )

    fun makeAllMoviesFata() = launchIO(
        safeAction = {
            moviesPagedData.transform {
                emit(getPagedMoviesUseCase.execute())
            }
        },
        onError = { processException(it) }
    )

    // Navigation between fragments:
    //  CategoryText(R.string.all) -> AllMovieFragment
    //  Other categories -> MoviesFragment
    fun navigateToCategory(category: Category) {
        _navigateToCategoryLiveEvent.postValue(category)
    }

    // Set AllMoviesFragment progress bar visibility
    fun setAllMoviesProgress(value: Boolean) {
        _allMoviesUpdateState.tryEmit(value)
    }

    // Bookmark view click processing
    fun onBookmarkClicked(movie: Movie) = launchIO(
        safeAction = {
            if (movie.isBookmark) {
                removeBookmarkUseCase.execute(movie)
            } else {
                addBookmarkUseCase.execute(movie)
            }
        },
        onError = { processException(it) }
    )

    // Navigate between fragments at main activity
    fun navigateToFlow(destination: Destination) {
        _navigationFlowLiveEvent.postValue(destination)
    }

    fun navigateTo(homeFlowDestinations: HomeFlowDestinations) {
        _navigateToLiveEvent.postValue(homeFlowDestinations)
    }

    fun getGenreOrCollectionRequestNameByResId(@StringRes resId: Int): String {
        val tmpRes = getCollectionRequestNameByResId(resId)

        return if (tmpRes != "") {
            tmpRes
        } else {
            getGenreRequestNameByResId(resId)
        }
    }

    private fun processException(throwable: Throwable) {
        launch(Dispatchers.Main) {
            _exceptionsFlow.tryEmit(
                throwable.toParsedException(::titleBuilder)
            )
        }
    }

    private fun titleBuilder(throwable: Throwable): String {
        return throwable.localizedMessage ?: getStringUseCase
            .execute(by.bashlikovvv.core.R.string.smth_went_wrong)
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
        R.string.comedies -> "комедия"
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
        private val addBookmarkUseCase: Provider<AddBookmarkUseCase>,
        private val removeBookmarkUseCase: Provider<RemoveBookmarkUseCase>,
        private val getStringUseCase: Provider<GetStringUseCase>
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == HomeScreenViewModel::class.java)
            return HomeScreenViewModel(
                getPagedMoviesUseCase.get(),
                getMoviesByGenreUseCase.get(),
                getMoviesByCollectionUseCase.get(),
                addBookmarkUseCase.get(),
                removeBookmarkUseCase.get(),
                getStringUseCase.get()
            ) as T
        }

    }

    companion object {
        val defaultCategory = CategoryText(R.string.all)

        val defaultMoviesCategory = listOf(CategoryTitle(R.string.top_250))
    }

}