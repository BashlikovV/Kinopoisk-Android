package by.bashlikovvv.kinopoisk_android.presentation.ui

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import by.bashlikovvv.core.domain.model.FlowDestinations
import by.bashlikovvv.core.ext.launchMain
import by.bashlikovvv.core.ext.setFragmentNavigationListener
import by.bashlikovvv.kinopoisk_android.R
import by.bashlikovvv.kinopoisk_android.databinding.ActivityMainBinding
import by.bashlikovvv.kinopoisk_android.presentation.KinopoiskApplication
import by.bashlikovvv.kinopoisk_android.presentation.flows.MoreFlowFragmentArgs
import by.bashlikovvv.kinopoisk_android.presentation.viewmodel.MainActivityViewModel
import by.bashlikovvv.moviedetailsscreen.presentation.ui.MovieDetailsFragmentArgs
import dagger.Lazy
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject internal lateinit var viewModelFactory: Lazy<MainActivityViewModel.Factory>

    private val viewModel: MainActivityViewModel by viewModels {
        viewModelFactory.get()
    }

    private val navController: NavController
        get() = findNavController(R.id.mainActivityFragmentContainer)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as KinopoiskApplication)
            .appComponent
            .inject(this)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addOnPreDrawListener()
        collectViewModelStates()
        setFragmentNavigationListener()
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

    private fun collectViewModelStates() {
        launchMain(
            safeAction = {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.exceptionsFlow
                        .collectLatest {
                            it
                                .getAlertDialog(this@MainActivity)
                                .show()
                        }
                }
            },
            exceptionHandler = viewModel.exceptionsHandler
        )
    }

    private fun setFragmentNavigationListener() {
        supportFragmentManager.setFragmentNavigationListener(this) { destination ->
            fragmentNavigationListener(destination)
        }
    }

    private fun fragmentNavigationListener(flowDestination: FlowDestinations) {
        when(flowDestination) {
            is FlowDestinations.HomeScreenFlow -> {
                navController.navigate(R.id.homeFlowFragment)
            }
            is FlowDestinations.MoreScreenFlow -> {
                navController.navigate(
                    R.id.moreFlowFragment,
                    MoreFlowFragmentArgs(flowDestination.categoryName).toBundle()
                )
            }
            is FlowDestinations.DetailsScreen -> {
                navController.navigate(
                    R.id.movieDetailsFragment,
                    MovieDetailsFragmentArgs(flowDestination.movieId).toBundle()
                )
            }
        }
    }

}