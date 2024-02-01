package by.bashlikovvv.kinopoisk_android.di

import android.app.Application
import by.bashlikovvv.bookmarksscreen.di.BookmarksScreenDependencies
import by.bashlikovvv.core.di.AppScope
import by.bashlikovvv.core.di.ApplicationQualifier
import by.bashlikovvv.homescreen.di.HomeScreenDependencies
import by.bashlikovvv.kinopoisk_android.presentation.ui.MainActivity
import by.bashlikovvv.moviedetailsscreen.di.MovieDetailsScreenDependencies
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@[AppScope Singleton Component(
    modules = [DataModule::class, DomainModule::class]
)]
interface AppComponent
    : HomeScreenDependencies,
    MovieDetailsScreenDependencies,
    BookmarksScreenDependencies {

    fun inject(mainActivity: MainActivity)

    @Component.Factory
    interface Factory {

        fun create(@[BindsInstance ApplicationQualifier] application: Application): AppComponent

    }

}