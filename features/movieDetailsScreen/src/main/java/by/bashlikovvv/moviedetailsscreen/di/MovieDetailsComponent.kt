package by.bashlikovvv.moviedetailsscreen.di

import androidx.annotation.RestrictTo
import androidx.lifecycle.ViewModel
import by.bashlikovvv.core.di.Feature
import by.bashlikovvv.core.domain.usecase.GetMovieByIdUseCase
import by.bashlikovvv.moviedetailsscreen.presentation.view.MovieDetailsFragment
import dagger.Component
import javax.inject.Scope
import kotlin.properties.Delegates

@Scope
annotation class MovieDetailsScreenScope

@[Feature MovieDetailsScreenScope Component(dependencies = [MovieDetailsScreenDependencies::class])]
interface MovieDetailsComponent {

    fun inject(movieDetailsFragment: MovieDetailsFragment)

    @Component.Builder
    interface Builder {

        fun deps(movieDetailsScreenDependencies: MovieDetailsScreenDependencies): Builder

        fun build(): MovieDetailsComponent

    }

}

interface MovieDetailsScreenDependencies {

    val getMovieByIdUseCase: GetMovieByIdUseCase

}

interface MovieScreenDependenciesProvider {

    @get:RestrictTo(RestrictTo.Scope.LIBRARY)
    var deps: MovieDetailsScreenDependencies

    companion object : MovieScreenDependenciesProvider by MovieDetailsScreenDependenciesStore
}

object MovieDetailsScreenDependenciesStore : MovieScreenDependenciesProvider {

    override var deps: MovieDetailsScreenDependencies by Delegates.notNull()

}

internal class MovieDetailsScreenComponentViewModel : ViewModel() {

    val movieDetailsScreenComponent = DaggerMovieDetailsComponent.builder()
        .deps(MovieDetailsScreenDependenciesStore.deps)
        .build()

}