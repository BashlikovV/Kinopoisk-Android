package by.bashlikovvv.kinopoisk_android.di

import android.app.Application
import by.bashlikovvv.core.di.AppScope
import by.bashlikovvv.core.di.ApplicationQualifier
import by.bashlikovvv.core.domain.repository.IBookmarksRepository
import by.bashlikovvv.core.domain.repository.IMoviesDetailsRepository
import by.bashlikovvv.core.domain.repository.IMoviesRepository
import by.bashlikovvv.core.domain.usecase.AddBookmarkUseCase
import by.bashlikovvv.core.domain.usecase.GetBookmarksByNameUseCase
import by.bashlikovvv.core.domain.usecase.GetBookmarksUseCase
import by.bashlikovvv.core.domain.usecase.GetMovieByIdUseCase
import by.bashlikovvv.core.domain.usecase.GetMovieDetailsByIdUseCase
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

@Module(includes = [DataModule::class])
class DomainModule {

    @[Provides AppScope]
    fun provideGetMovieByIdUseCaseUseCase(
        moviesRepository: IMoviesRepository
    ): GetMovieByIdUseCase {
        return GetMovieByIdUseCase(moviesRepository)
    }

    @[Provides AppScope]
    fun provideGetMoviesByNameUseCase(
        moviesRepository: IMoviesRepository
    ): GetMoviesByNameUseCase {
        return GetMoviesByNameUseCase(moviesRepository)
    }

    @[Provides AppScope]
    fun provideGetPagedMoviesUseCase(
        moviesRepository: IMoviesRepository
    ): GetPagedMoviesUseCase {
        return GetPagedMoviesUseCase(moviesRepository)
    }

    @[Provides AppScope]
    fun provideGetMoviesByGenreUseCase(
        moviesRepository: IMoviesRepository
    ): GetMoviesByGenreUseCase {
        return GetMoviesByGenreUseCase(moviesRepository)
    }

    @[Provides AppScope]
    fun provideGetMoviesByCollectionUseCase(
        moviesRepository: IMoviesRepository
    ): GetMoviesByCollectionUseCase {
        return GetMoviesByCollectionUseCase(moviesRepository)
    }

    @[Provides AppScope]
    fun provideGetStringUseCase(
        @ApplicationQualifier application: Application
    ): GetStringUseCase {
        return GetStringUseCase(application)
    }

    @[Provides AppScope]
    fun provideAddBookmarkUseCase(
        bookmarksRepository: IBookmarksRepository,
        moviesDetailsRepository: IMoviesDetailsRepository
    ): AddBookmarkUseCase {
        return AddBookmarkUseCase(bookmarksRepository, moviesDetailsRepository)
    }

    @[Provides AppScope]
    fun provideRemoveBookmarkUseCase(
        bookmarksRepository: IBookmarksRepository
    ): RemoveBookmarkUseCase {
        return RemoveBookmarkUseCase(bookmarksRepository)
    }

    @[Provides AppScope]
    fun provideGetBookmarksUseCase(
        bookmarksRepository: IBookmarksRepository
    ): GetBookmarksUseCase {
        return GetBookmarksUseCase(bookmarksRepository)
    }

    @[Provides AppScope]
    fun provideGetBookmarksByNameUseCase(
        bookmarksRepository: IBookmarksRepository
    ): GetBookmarksByNameUseCase {
        return GetBookmarksByNameUseCase(bookmarksRepository)
    }

    @[Provides AppScope]
    fun provideGetPagedMoviesByGenreUseCase(
        moviesRepository: IMoviesRepository
    ): GetPagedMoviesByGenreUseCase {
        return GetPagedMoviesByGenreUseCase(moviesRepository)
    }

    @[Provides AppScope]
    fun provideGetPagedMoviesByGenreAndNameUseCase(
        moviesRepository: IMoviesRepository
    ): GetPagedMoviesByGenreAndNameUseCase {
        return GetPagedMoviesByGenreAndNameUseCase(moviesRepository)
    }

    @[Provides AppScope]
    fun provideGetMovieDetailsByIdUseCase(
        moviesDetailsRepository: IMoviesDetailsRepository
    ): GetMovieDetailsByIdUseCase {
        return GetMovieDetailsByIdUseCase(moviesDetailsRepository)
    }

}