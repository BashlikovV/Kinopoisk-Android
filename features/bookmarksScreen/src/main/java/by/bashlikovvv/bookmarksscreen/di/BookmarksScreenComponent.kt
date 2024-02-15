package by.bashlikovvv.bookmarksscreen.di

import by.bashlikovvv.bookmarksscreen.presentation.ui.BookmarksFragment
import by.bashlikovvv.core.di.Feature
import dagger.Subcomponent
import javax.inject.Scope

@Scope
annotation class BookmarksScreenScope

@[Feature BookmarksScreenScope Subcomponent]
interface BookmarksScreenComponent {

    @Subcomponent.Factory
    interface Factory {

        fun create(): BookmarksScreenComponent

    }

    fun inject(bookmarksFragment: BookmarksFragment)

}