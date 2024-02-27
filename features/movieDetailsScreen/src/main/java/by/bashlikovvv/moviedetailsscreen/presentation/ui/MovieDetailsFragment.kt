package by.bashlikovvv.moviedetailsscreen.presentation.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import by.bashlikovvv.moviedetailsscreen.databinding.FragmentMovieDetailsBinding
import by.bashlikovvv.moviedetailsscreen.di.MovieDetailsScreenComponentProvider
import by.bashlikovvv.moviedetailsscreen.presentation.viewmodel.MovieDetailsScreenViewModel
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieDetailsFragment : BottomSheetDialogFragment() {

    @Inject internal lateinit var viewModel: MovieDetailsScreenViewModel

    private var movieId: Long? = null

    override fun onAttach(context: Context) {
        (requireContext().applicationContext as MovieDetailsScreenComponentProvider)
            .provideMovieDetailsScreenComponent()
            .inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieId = requireArguments().getLong(KEY_MOVIE_ID)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)

        loadMovie()
        collectViewModelStates(binding)

        return binding.root
    }

    private fun loadMovie() {
        lifecycleScope.launch {
            viewModel.getMovieById(movieId ?: 0)
        }
    }

    private fun collectViewModelStates(binding: FragmentMovieDetailsBinding) {
        lifecycleScope.launch {
            viewModel.movie
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collectLatest { movie ->
                setBitmapWithGlide(movie.poster, binding.posterImageView)
                binding.descriptionImageView.text = movie.shortDescription
            }
        }
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
        const val KEY_MOVIE_ID = "KEY_MOVIE_ID"
    }

}