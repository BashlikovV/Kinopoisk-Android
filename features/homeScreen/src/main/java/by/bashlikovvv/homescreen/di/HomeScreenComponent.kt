package by.bashlikovvv.homescreen.di

import by.bashlikovvv.core.di.Feature
import by.bashlikovvv.homescreen.presentation.ui.AllMoviesFragment
import by.bashlikovvv.homescreen.presentation.ui.HomeScreenFragment
import by.bashlikovvv.homescreen.presentation.ui.MoviesFragment
import dagger.Subcomponent
import javax.inject.Scope

@Scope
annotation class HomeScreenScope

@[Feature HomeScreenScope Subcomponent]
interface HomeScreenComponent {

    fun inject(homeScreenFragment: HomeScreenFragment)

    fun inject(allMoviesFragment: AllMoviesFragment)

    fun inject(moviesFragment: MoviesFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): HomeScreenComponent
    }

}