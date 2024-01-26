package by.bashlikovvv.homescreen.presentation.adapter.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import by.bashlikovvv.core.domain.model.Movie
import by.bashlikovvv.homescreen.databinding.MoviesListItemBinding
import by.bashlikovvv.homescreen.domain.model.CategoryMovie
import com.bumptech.glide.Glide

class MovieItemViewHolder(
    private val binding: MoviesListItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CategoryMovie, onMovieClicked: (Movie) -> Unit) {
        binding.let {
            setBitmapWithGlide(item.movie.poster, it.posterImageView)
            it.movieNameTextView.text = item.movie.name
            it.movieDescriptionTextView.text = item.movie.description
            it.root.setOnClickListener { onMovieClicked(item.movie) }
        }
    }

    private fun setBitmapWithGlide(url: String, view: ImageView) {
        Glide.with(view)
            .asBitmap()
            .load(url)
            .into(view)
    }

    companion object {

        operator fun invoke(parent: ViewGroup): MovieItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)

            return MovieItemViewHolder(
                MoviesListItemBinding.inflate(
                    layoutInflater, parent, false
                )
            )
        }

    }

}