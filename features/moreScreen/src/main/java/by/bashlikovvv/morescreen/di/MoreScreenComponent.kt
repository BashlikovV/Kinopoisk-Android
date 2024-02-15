package by.bashlikovvv.morescreen.di

import by.bashlikovvv.core.di.Feature
import by.bashlikovvv.morescreen.presentation.ui.MoreFragment
import dagger.Subcomponent
import javax.inject.Scope

@Scope
annotation class MoreScreenScope

@[Feature MoreScreenScope Subcomponent]
interface MoreScreenComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): MoreScreenComponent

    }

    fun inject(moreFragment: MoreFragment)

}