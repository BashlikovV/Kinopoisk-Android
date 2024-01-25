package by.bashlikovvv.kinopoisk_android.presentation

import android.app.Application
import by.bashlikovvv.homescreen.di.HomeScreenDependenciesStore
import by.bashlikovvv.kinopoisk_android.di.AppComponent
import by.bashlikovvv.kinopoisk_android.di.DaggerAppComponent

class KinopoiskApplication : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory()
            .create(this)
    }

    override fun onCreate() {
        super.onCreate()
        HomeScreenDependenciesStore.deps = appComponent
    }

}