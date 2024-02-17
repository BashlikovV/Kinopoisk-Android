package by.bashlikovvv.kinopoisk_android.di

import android.app.Application
import by.bashlikovvv.core.di.AppScope
import by.bashlikovvv.core.di.ApplicationQualifier
import by.bashlikovvv.core.domain.repository.IBookmarksRepository
import by.bashlikovvv.core.domain.repository.IMoviesRepository
import by.bashlikovvv.core.domain.usecase.AddBookmarkUseCase
import by.bashlikovvv.core.domain.usecase.GetBookmarksByNameUseCase
import by.bashlikovvv.core.domain.usecase.GetBookmarksUseCase
import by.bashlikovvv.core.domain.usecase.GetMovieByIdUseCase
import by.bashlikovvv.core.domain.usecase.GetMoviesByCollectionUseCase
import by.bashlikovvv.core.domain.usecase.GetMoviesByGenreUseCase
import by.bashlikovvv.core.domain.usecase.GetMoviesByNameUseCase
import by.bashlikovvv.core.domain.usecase.GetPagedMoviesByGenreAndNameUseCase
import by.bashlikovvv.core.domain.usecase.GetPagedMoviesByGenreUseCase
import by.bashlikovvv.core.domain.usecase.GetPagedMoviesUseCase
import by.bashlikovvv.core.domain.usecase.GetStringUseCase
import by.bashlikovvv.core.domain.usecase.RemoveBookmarkUseCase
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

    @[Provides Inject AppScope]
    fun provideAddBookmarkUseCase(
        bookmarksRepository: IBookmarksRepository
    ): AddBookmarkUseCase {
        return AddBookmarkUseCase(bookmarksRepository)
    }

    @[Provides Inject AppScope]
    fun provideRemoveBookmarkUseCase(
        bookmarksRepository: IBookmarksRepository
    ): RemoveBookmarkUseCase {
        return RemoveBookmarkUseCase(bookmarksRepository)
    }

    @[Provides Inject AppScope]
    fun provideGetBookmarksUseCase(
        bookmarksRepository: IBookmarksRepository
    ): GetBookmarksUseCase {
        return GetBookmarksUseCase(bookmarksRepository)
    }

    @[Provides Inject AppScope]
    fun provideGetBookmarksByNameUseCase(
        bookmarksRepository: IBookmarksRepository
    ): GetBookmarksByNameUseCase {
        return GetBookmarksByNameUseCase(bookmarksRepository)
    }

    @[Provides Inject AppScope]
    fun provideGetPagedMoviesByGenreUseCase(
        moviesRepository: IMoviesRepository
    ): GetPagedMoviesByGenreUseCase {
        return GetPagedMoviesByGenreUseCase(moviesRepository)
    }

    @[Provides Inject AppScope]
    fun provideGetPagedMoviesByGenreAndNameUseCase(
        moviesRepository: IMoviesRepository
    ): GetPagedMoviesByGenreAndNameUseCase {
        return GetPagedMoviesByGenreAndNameUseCase(moviesRepository)
    }

}