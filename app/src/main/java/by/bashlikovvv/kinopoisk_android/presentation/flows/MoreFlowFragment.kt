package by.bashlikovvv.kinopoisk_android.presentation.flows

import android.os.Bundle
import androidx.core.os.bundleOf
import by.bashlikovvv.core.base.BaseFlowFragment
import by.bashlikovvv.kinopoisk_android.R

class MoreFlowFragment : BaseFlowFragment() {
    override fun graphRes(): Int = R.navigation.more_navigation

    override fun flowArgs(): Bundle = bundleOf()
}