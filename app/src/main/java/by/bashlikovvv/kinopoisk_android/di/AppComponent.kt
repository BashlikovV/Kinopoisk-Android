package by.bashlikovvv.kinopoisk_android.di

import android.app.Application
import by.bashlikovvv.bookmarksscreen.di.BookmarksScreenComponent
import by.bashlikovvv.core.di.AppScope
import by.bashlikovvv.core.di.ApplicationQualifier
import by.bashlikovvv.homescreen.di.HomeScreenComponent
import by.bashlikovvv.kinopoisk_android.presentation.ui.MainActivity
import by.bashlikovvv.morescreen.di.MoreScreenComponent
import by.bashlikovvv.moviedetailsscreen.di.MovieDetailsComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@[AppScope Singleton Component(
    modules = [DataModule::class, DomainModule::class]
)]
interface AppComponent {

    @Component.Factory
    interface Factory {

        fun create(@[BindsInstance ApplicationQualifier] application: Application): AppComponent

    }

    fun inject(mainActivity: MainActivity)

    fun bookmarksScreenComponent(): BookmarksScreenComponent.Factory

    fun homeScreenComponent(): HomeScreenComponent.Factory

    fun moreScreenComponent(): MoreScreenComponent.Factory

    fun movieDetailsScreenComponent(): MovieDetailsComponent.Factory

}