package by.bashlikovvv.kinopoisk_android.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import by.bashlikovvv.core.domain.model.Destination
import by.bashlikovvv.core.util.setFragmentNavigationListener
import by.bashlikovvv.kinopoisk_android.R
import by.bashlikovvv.kinopoisk_android.databinding.ActivityMainBinding
import by.bashlikovvv.kinopoisk_android.presentation.KinopoiskApplication
import by.bashlikovvv.kinopoisk_android.presentation.viewmodel.MainActivityViewModel

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
        supportFragmentManager.setFragmentNavigationListener(this) { destination ->
            fragmentNavigationListener(destination)
        }
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

    private fun fragmentNavigationListener(destination: Destination) {
        when(destination) {
            is Destination.HomeScreen -> {
                navController.navigate(R.id.homeScreenFragment)
            }
        }
    }

}