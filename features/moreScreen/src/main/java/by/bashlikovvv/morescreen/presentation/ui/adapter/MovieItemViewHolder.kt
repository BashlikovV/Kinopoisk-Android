package by.bashlikovvv.morescreen.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import by.bashlikovvv.core.domain.model.Movie
import by.bashlikovvv.morescreen.databinding.MoviesListItemBinding
import com.bumptech.glide.Glide

class MovieItemViewHolder(
    private val binding: MoviesListItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: Movie,
        movieItemViewHolderClickListener: MovieItemViewHolderClickListener
    ) {
        binding.let {
            setBitmapWithGlide(item.poster, it.posterImageView)
            it.movieNameTextView.text = item.name
            it.movieDescriptionTextView.text = item.description
            it.root.setOnClickListener { movieItemViewHolderClickListener.onMovieClicked(item) }
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

    interface MovieItemViewHolderClickListener {

        fun onMovieClicked(movie: Movie)

    }

}