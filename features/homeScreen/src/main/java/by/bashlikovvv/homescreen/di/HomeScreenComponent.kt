package by.bashlikovvv.homescreen.di

import androidx.annotation.RestrictTo
import androidx.lifecycle.ViewModel
import by.bashlikovvv.core.di.Feature
import by.bashlikovvv.core.domain.usecase.GetMovieByIdUseCase
import by.bashlikovvv.core.domain.usecase.GetMoviesByCollectionUseCase
import by.bashlikovvv.core.domain.usecase.GetMoviesByGenreUseCase
import by.bashlikovvv.core.domain.usecase.GetMoviesByNameUseCase
import by.bashlikovvv.core.domain.usecase.GetPagedMoviesUseCase
import by.bashlikovvv.core.domain.usecase.GetStringUseCase
import by.bashlikovvv.homescreen.presentation.view.AllMoviesFragment
import by.bashlikovvv.homescreen.presentation.view.HomeScreenFragment
import by.bashlikovvv.homescreen.presentation.view.MoviesFragment
import dagger.Component
import javax.inject.Scope
import kotlin.properties.Delegates

@Scope
annotation class HomeScreenScope

@[Feature HomeScreenScope Component(dependencies = [HomeScreenDependencies::class])]
interface HomeScreenComponent {

    fun inject(homeScreenFragment: HomeScreenFragment)

    fun inject(allMoviesFragment: AllMoviesFragment)

    fun inject(moviesFragment: MoviesFragment)

    @Component.Builder
    interface Builder {

        fun deps(homeScreenDependencies: HomeScreenDependencies): Builder

        fun build(): HomeScreenComponent

    }

}

interface HomeScreenDependencies {

    val getPagedMoviesUseCase: GetPagedMoviesUseCase

    val getMoviesByGenreUseCase: GetMoviesByGenreUseCase

    val getMoviesByCollectionUseCase: GetMoviesByCollectionUseCase

}

interface HomeScreenDependenciesProvider {

    @get:RestrictTo(RestrictTo.Scope.LIBRARY)
    var deps: HomeScreenDependencies

    companion object : HomeScreenDependenciesProvider by HomeScreenDependenciesStore

}

object HomeScreenDependenciesStore : HomeScreenDependenciesProvider {

    override var deps: HomeScreenDependencies by Delegates.notNull()

}

internal class HomeScreenComponentViewModel : ViewModel() {

    val homeScreenComponent = DaggerHomeScreenComponent.builder()
        .deps(HomeScreenDependenciesStore.deps)
        .build()

}