package by.bashlikovvv.moviedetailsscreen.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.bashlikovvv.moviedetailsscreen.databinding.FragmentMovieDetailsBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MovieDetailsFragment : BottomSheetDialogFragment() {

    private var movieId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieId = requireArguments().getInt(KEY_MOVIE_ID)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    companion object {
        const val KEY_MOVIE_ID = "KEY_MOVIE_ID"
    }

}