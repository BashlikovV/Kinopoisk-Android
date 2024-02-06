package by.bashlikovvv.morescreen.di

import androidx.annotation.RestrictTo
import androidx.lifecycle.ViewModel
import by.bashlikovvv.core.di.Feature
import by.bashlikovvv.morescreen.presentation.ui.MoreFragment
import dagger.Component
import javax.inject.Scope
import kotlin.properties.Delegates

@Scope
annotation class MoreScreenScope

@[
    Feature
    MoreScreenScope
    Component(
        dependencies = [MoreScreenDependencies::class],
        modules = [MoreScreenModule::class]
    )

]
interface MoreScreenComponent {

    fun inject(moreFragment: MoreFragment)

    @Component.Builder
    interface Builder {

        fun deps(moreScreenDependencies: MoreScreenDependencies): Builder

        fun build(): MoreScreenComponent

    }

}

interface MoreScreenDependencies {



}

interface MoreScreenDependenciesProvider {

    @get:RestrictTo(RestrictTo.Scope.LIBRARY)
    var deps: MoreScreenDependencies

    companion object : MoreScreenDependenciesProvider by MoreScreenDependenciesStore

}

object MoreScreenDependenciesStore : MoreScreenDependenciesProvider {

    override var deps: MoreScreenDependencies by Delegates.notNull()

}

internal class MoreScreenComponentViewModel : ViewModel() {

    val moreScreenComponent = DaggerMoreScreenComponent.builder()
        .deps(MoreScreenDependenciesStore.deps)
        .build()

}