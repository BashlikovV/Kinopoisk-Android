package by.bashlikovvv.kinopoisk_android.presentation.flows

import android.os.Bundle
import androidx.navigation.fragment.navArgs
import by.bashlikovvv.core.base.BaseFlowFragment
import by.bashlikovvv.kinopoisk_android.R

class MoreFlowFragment : BaseFlowFragment() {

    private val args: MoreFlowFragmentArgs by navArgs()

    override fun graphRes(): Int = R.navigation.more_navigation

    override fun flowArgs(): Bundle = args.toBundle()
}