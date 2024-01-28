package by.bashlikovvv.kinopoisk_android.di

import android.app.Application
import by.bashlikovvv.core.di.AppScope
import by.bashlikovvv.core.di.ApplicationQualifier
import by.bashlikovvv.core.domain.repository.IMoviesRepository
import by.bashlikovvv.core.domain.usecase.GetMovieByIdUseCase
import by.bashlikovvv.core.domain.usecase.GetMoviesByCollectionUseCase
import by.bashlikovvv.core.domain.usecase.GetMoviesByGenreUseCase
import by.bashlikovvv.core.domain.usecase.GetMoviesByNameUseCase
import by.bashlikovvv.core.domain.usecase.GetPagedMoviesUseCase
import by.bashlikovvv.core.domain.usecase.GetStringUseCase
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

    @[Provides Inject AppScope]
    fun provideGetMoviesByGenreUseCase(
        moviesRepository: IMoviesRepository
    ): GetMoviesByGenreUseCase {
        return GetMoviesByGenreUseCase(moviesRepository)
    }

    @[Provides Inject AppScope]
    fun provideGetMoviesByCollectionUseCase(
        moviesRepository: IMoviesRepository
    ): GetMoviesByCollectionUseCase {
        return GetMoviesByCollectionUseCase(moviesRepository)
    }

    @[Provides Inject AppScope]
    fun provideGetStringUseCase(
        @ApplicationQualifier application: Application
    ): GetStringUseCase {
        return GetStringUseCase(application)
    }

}