package by.bashlikovvv.moviedetailsscreen.presentation.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.bashlikovvv.moviedetailsscreen.databinding.FragmentMovieDetailsBinding
import by.bashlikovvv.moviedetailsscreen.di.MovieDetailsScreenComponentViewModel
import by.bashlikovvv.moviedetailsscreen.presentation.viewmodel.MovieDetailsScreenViewModel
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieDetailsFragment : BottomSheetDialogFragment() {

    @Inject internal lateinit var viewModel: MovieDetailsScreenViewModel

    private var movieId: Int? = null

    override fun onAttach(context: Context) {
        ViewModelProvider(this)[MovieDetailsScreenComponentViewModel::class.java]
            .movieDetailsScreenComponent
            .inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieId = requireArguments().getInt(KEY_MOVIE_ID)
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
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(androidx.lifecycle.Lifecycle.State.RESUMED) {
                viewModel.getMovieById(movieId ?: 0)
            }
        }
    }

    private fun collectViewModelStates(binding: FragmentMovieDetailsBinding) {
        lifecycleScope.launch {
            viewModel.movie.collectLatest { movie ->
                setBitmapWithGlide(movie.poster, binding.posterImageView)
                binding.descriptionImageView.text = movie.description
            }
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