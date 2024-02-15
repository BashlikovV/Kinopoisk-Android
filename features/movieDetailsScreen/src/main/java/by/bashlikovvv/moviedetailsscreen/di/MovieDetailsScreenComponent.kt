package by.bashlikovvv.moviedetailsscreen.di

import by.bashlikovvv.core.di.Feature
import by.bashlikovvv.moviedetailsscreen.presentation.ui.MovieDetailsFragment
import dagger.Subcomponent
import javax.inject.Scope

@Scope
annotation class MovieDetailsScreenScope

@[Feature MovieDetailsScreenScope Subcomponent]
interface MovieDetailsComponent {

    @Subcomponent.Factory
    interface Factory {

        fun create(): MovieDetailsComponent

    }

    fun inject(movieDetailsFragment: MovieDetailsFragment)

}
