package by.bashlikovvv.kinopoisk_android.di

import by.bashlikovvv.bookmarksscreen.presentation.ui.BookmarksFragment
import by.bashlikovvv.core.di.Feature
import dagger.Component
import javax.inject.Scope

@Scope
annotation class BookmarksScreenScope

@[Feature BookmarksScreenScope Component(dependencies = [AppComponent::class])]
interface BookmarksScreenComponent {

    fun inject(bookmarksFragment: BookmarksFragment)

    @Component.Builder
    interface Builder {

        fun build(): BookmarksScreenComponent

    }

}