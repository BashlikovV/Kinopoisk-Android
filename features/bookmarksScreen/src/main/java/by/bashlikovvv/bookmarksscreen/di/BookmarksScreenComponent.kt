package by.bashlikovvv.bookmarksscreen.di

import androidx.annotation.RestrictTo
import androidx.lifecycle.ViewModel
import by.bashlikovvv.bookmarksscreen.presentation.ui.BookmarksFragment
import by.bashlikovvv.core.di.Feature
import by.bashlikovvv.core.domain.usecase.GetBookmarksUseCase
import by.bashlikovvv.core.domain.usecase.RemoveBookmarkUseCase
import dagger.Component
import javax.inject.Scope
import kotlin.properties.Delegates

@Scope
annotation class BookmarksScreenScope

@[Feature BookmarksScreenScope Component(dependencies = [BookmarksScreenDependencies::class])]
interface BookmarksScreenComponent {

    fun inject(bookmarksFragment: BookmarksFragment)

    @Component.Builder
    interface Builder {

        fun deps(bookmarksScreenDependencies: BookmarksScreenDependencies): Builder

        fun build(): BookmarksScreenComponent

    }

}

interface BookmarksScreenDependencies {

    val getBookmarksUseCase: GetBookmarksUseCase

    val removeBookmarkUseCase: RemoveBookmarkUseCase

}

interface BookmarksScreenDependenciesProvider {

    @get:RestrictTo(RestrictTo.Scope.LIBRARY)
    var deps: BookmarksScreenDependencies

    companion object : BookmarksScreenDependenciesProvider by BookmarksScreenDependenciesStore

}

object BookmarksScreenDependenciesStore : BookmarksScreenDependenciesProvider {

    override var deps: BookmarksScreenDependencies by Delegates.notNull()

}

internal class BookmarksScreenComponentViewModel : ViewModel() {

    val bookmarksScreenComponent = DaggerBookmarksScreenComponent.builder()
        .deps(BookmarksScreenDependenciesStore.deps)
        .build()

}