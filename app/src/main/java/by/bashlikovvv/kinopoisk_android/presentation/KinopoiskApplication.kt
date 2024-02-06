package by.bashlikovvv.kinopoisk_android.presentation

import android.app.Application
import by.bashlikovvv.bookmarksscreen.di.BookmarksScreenDependenciesStore
import by.bashlikovvv.homescreen.di.HomeScreenDependenciesStore
import by.bashlikovvv.kinopoisk_android.di.AppComponent
import by.bashlikovvv.kinopoisk_android.di.DaggerAppComponent
import by.bashlikovvv.morescreen.di.MoreScreenDependenciesStore
import by.bashlikovvv.moviedetailsscreen.di.MovieDetailsScreenDependenciesStore

class KinopoiskApplication : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory()
            .create(this)
    }

    override fun onCreate() {
        super.onCreate()
        HomeScreenDependenciesStore.deps = appComponent
        MovieDetailsScreenDependenciesStore.deps = appComponent
        BookmarksScreenDependenciesStore.deps = appComponent
        MoreScreenDependenciesStore.deps = appComponent
    }

}