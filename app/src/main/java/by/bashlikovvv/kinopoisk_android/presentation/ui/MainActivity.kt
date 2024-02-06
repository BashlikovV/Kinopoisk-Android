package by.bashlikovvv.kinopoisk_android.presentation.ui

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import by.bashlikovvv.core.domain.model.Destination
import by.bashlikovvv.core.util.setFragmentNavigationListener
import by.bashlikovvv.kinopoisk_android.R
import by.bashlikovvv.kinopoisk_android.databinding.ActivityMainBinding
import by.bashlikovvv.kinopoisk_android.presentation.KinopoiskApplication
import by.bashlikovvv.kinopoisk_android.presentation.viewmodel.MainActivityViewModel
import by.bashlikovvv.morescreen.presentation.ui.MoreFragment
import by.bashlikovvv.moviedetailsscreen.presentation.view.MovieDetailsFragment

class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    private val navController: NavController by lazy(LazyThreadSafetyMode.NONE) {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.mainActivityFragmentContainer) as NavHostFragment

        navHostFragment.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as KinopoiskApplication)
            .appComponent
            .inject(this)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addOnPreDrawListener()
        setFragmentNavigationListener()
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)
    }

    private fun addOnPreDrawListener() {
        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    return if (viewModel.isReady.value) {
                        content.viewTreeObserver.removeOnPreDrawListener(this)

                        true
                    } else {
                        false
                    }
                }
            }
        )
    }

    private fun setFragmentNavigationListener() {
        supportFragmentManager.setFragmentNavigationListener(this) { destination ->
            fragmentNavigationListener(destination)
        }
    }

    private fun fragmentNavigationListener(destination: Destination) {
        when(destination) {
            is Destination.HomeScreen -> {
                navController.navigate(R.id.homeScreenFragment)
            }
            is Destination.MovieDetailsScreen -> {
                navController.navigate(
                    R.id.movieDetailsFragment,
                    bundleOf(MovieDetailsFragment.KEY_MOVIE_ID to destination.movieId)
                )
            }
            is Destination.MoreScreen -> {
                navController.navigate(
                    R.id.moreFragment,
                    bundleOf(MoreFragment.KEY_CATEGORY_NAME to destination.categoryName)
                )
            }
        }
    }

}