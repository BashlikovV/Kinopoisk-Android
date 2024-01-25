package by.bashlikovvv.kinopoisk_android.di

import by.bashlikovvv.core.di.AppScope
import by.bashlikovvv.core.domain.repository.IMoviesRepository
import by.bashlikovvv.core.domain.usecase.GetMovieByIdUseCase
import by.bashlikovvv.core.domain.usecase.GetMoviesByNameUseCase
import by.bashlikovvv.core.domain.usecase.GetPagedMoviesUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Inject

@Module(includes = [DataModule::class])
class DomainModule {

    @[Provides Inject AppScope]
    fun provideGetMovieByIdUseCaseUseCase(
        moviesRepository: IMoviesRepository
    ): GetMovieByIdUseCase {
        return GetMovieByIdUseCase(moviesRepository)
    }

    @[Provides Inject AppScope]
    fun provideGetMoviesByNameUseCase(
        moviesRepository: IMoviesRepository
    ): GetMoviesByNameUseCase {
        return GetMoviesByNameUseCase(moviesRepository)
    }

    @[Provides Inject AppScope]
    fun provideGetPagedMoviesUseCase(
        moviesRepository: IMoviesRepository
    ): GetPagedMoviesUseCase {
        return GetPagedMoviesUseCase(moviesRepository)
    }
}