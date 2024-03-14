package by.bashlikovvv.kinopoisk_android.presentation.flows

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.ui.NavigationUI
import by.bashlikovvv.core.base.BaseFlowFragment
import by.bashlikovvv.homescreen.presentation.view.CustomBottomNavigationView
import by.bashlikovvv.kinopoisk_android.R

class HomeFlowFragment : BaseFlowFragment() {

    override fun getLayoutResource(): Int = R.layout.home_flow_layout

    override fun getContainerId(): Int = R.id.homeFlowFragmentContainer

    override fun init(root: View) {
        val bottomNavBar = root
            .findViewById<CustomBottomNavigationView>(R.id.bottomNavigationView)

        NavigationUI.setupWithNavController(bottomNavBar, flowNavController)
    }

    override fun graphRes(): Int = R.navigation.home_navigation

    override fun flowArgs(): Bundle = bundleOf()
}