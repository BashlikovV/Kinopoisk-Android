package by.bashlikovvv.kinopoisk_android.presentation

import android.app.Application
import by.bashlikovvv.bookmarksscreen.di.BookmarksScreenComponent
import by.bashlikovvv.bookmarksscreen.di.BookmarksScreenComponentProvider
import by.bashlikovvv.homescreen.di.HomeScreenComponent
import by.bashlikovvv.homescreen.di.HomeScreenComponentProvider
import by.bashlikovvv.kinopoisk_android.di.AppComponent
import by.bashlikovvv.kinopoisk_android.di.DaggerAppComponent
import by.bashlikovvv.morescreen.di.MoreScreenComponent
import by.bashlikovvv.morescreen.di.MoreScreenComponentProvider
import by.bashlikovvv.moviedetailsscreen.di.MovieDetailsComponent
import by.bashlikovvv.moviedetailsscreen.di.MovieDetailsScreenComponentProvider

class KinopoiskApplication
    : Application(),
    BookmarksScreenComponentProvider,
    HomeScreenComponentProvider,
    MoreScreenComponentProvider,
    MovieDetailsScreenComponentProvider {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory()
            .create(this)
    }

    override fun provideBookmarksScreenComponent(): BookmarksScreenComponent {
        return appComponent.bookmarksScreenComponent().create()
    }

    override fun provideHomeScreenComponent(): HomeScreenComponent {
        return appComponent.homeScreenComponent().create()
    }

    override fun provideMoreScreenComponent(): MoreScreenComponent {
        return appComponent.moreScreenComponent().create()
    }

    override fun provideMovieDetailsScreenComponent(): MovieDetailsComponent {
        return appComponent.movieDetailsScreenComponent().create()
    }

}