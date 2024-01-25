package by.bashlikovvv.kinopoisk_android.di

import android.app.Application
import by.bashlikovvv.core.di.AppScope
import by.bashlikovvv.core.di.ApplicationQualifier
import by.bashlikovvv.homescreen.di.HomeScreenDependencies
import by.bashlikovvv.kinopoisk_android.presentation.view.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@[AppScope Singleton Component(
    modules = [DataModule::class, DomainModule::class]
)]
interface AppComponent
    : HomeScreenDependencies {

    fun inject(mainActivity: MainActivity)

    @Component.Factory
    interface Factory {

        fun create(@[BindsInstance ApplicationQualifier] application: Application): AppComponent

    }

}