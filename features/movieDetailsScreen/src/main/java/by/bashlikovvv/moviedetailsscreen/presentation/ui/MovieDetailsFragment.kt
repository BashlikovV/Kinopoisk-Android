package by.bashlikovvv.moviedetailsscreen.presentation.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.flowWithLifecycle
import by.bashlikovvv.core.base.BaseBottomSheetDialogFragment
import by.bashlikovvv.core.ext.launchMain
import by.bashlikovvv.moviedetailsscreen.databinding.FragmentMovieDetailsBinding
import by.bashlikovvv.moviedetailsscreen.di.MovieDetailsScreenComponentProvider
import by.bashlikovvv.moviedetailsscreen.presentation.viewmodel.MovieDetailsScreenViewModel
import com.bumptech.glide.Glide
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class MovieDetailsFragment : BaseBottomSheetDialogFragment<FragmentMovieDetailsBinding>() {

    @Inject internal lateinit var viewModel: MovieDetailsScreenViewModel

    private val movieId: Long? by lazy {
        requireArguments().getLong(KEY_MOVIE_ID)
    }

    override fun onAttach(context: Context) {
        (requireContext().applicationContext as MovieDetailsScreenComponentProvider)
            .provideMovieDetailsScreenComponent()
            .inject(this)
        super.onAttach(context)
    }

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMovieDetailsBinding {
        return FragmentMovieDetailsBinding.inflate(inflater, container, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        loadMovie()
        collectViewModelStates()

        return binding.root
    }

    private fun loadMovie() {
        launchMain(
            safeAction = { viewModel.getMovieById(movieId ?: 0) },
            exceptionHandler = viewModel.exceptionsHandler
        )
    }

    private fun collectViewModelStates() {
        launchMain(
            safeAction = {
                viewModel.movie
                    .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                    .collectLatest { movie ->
                        setBitmapWithGlide(movie.poster, binding.posterImageView)
                        binding.descriptionImageView.text = movie.shortDescription
                    }
            },
            exceptionHandler = viewModel.exceptionsHandler
        )
        launchMain(
            safeAction = {
                viewModel.exceptionsFlow
                    .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                    .collectLatest {
                        it
                            .getAlertDialog(requireContext())
                            .show()
                    }
            },
            exceptionHandler = viewModel.exceptionsHandler
        )
        viewModel.exceptions.observe(viewLifecycleOwner) { exception ->
            binding.descriptionImageView.text = exception
        }
    }

    private fun setBitmapWithGlide(url: String, view: ImageView) {
        Glide.with(view)
            .asBitmap()
            .load(url)
            .into(view)
    }

    companion object {
        const val KEY_MOVIE_ID = "movieId"
    }

}