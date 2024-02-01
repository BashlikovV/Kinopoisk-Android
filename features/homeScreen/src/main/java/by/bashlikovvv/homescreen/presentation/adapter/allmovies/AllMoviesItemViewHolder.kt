package by.bashlikovvv.homescreen.presentation.adapter.allmovies

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import by.bashlikovvv.core.domain.model.Movie
import by.bashlikovvv.homescreen.databinding.AllMoviesListItemBinding
import by.bashlikovvv.homescreen.presentation.view.BookmarkView
import com.bumptech.glide.Glide

class AllMoviesItemViewHolder(
    private val binding: AllMoviesListItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Movie, onClickListener: AllMoviesItemViewHolderClickListener) {
        binding.let {
            setBitmapWithGlide(item.poster, it.movieImageView)
            it.movieNameTextView.text = item.name
            if (item.isBookmark) {
                it.bookamrkView.startMoveBookmark()
            }
            it.bookamrkView.setOnClickListener { view ->
                (view as BookmarkView).notifyViewCLicked()
                onClickListener.onBookmarkClick(item)
            }
            it.root.setOnClickListener { onClickListener.onMovieClick(item) }
        }

    }

    private fun setBitmapWithGlide(url: String, view: ImageView) {
        Glide.with(view)
            .asBitmap()
            .load(url)
            .into(view)
    }

    companion object {

        operator fun invoke(parent: ViewGroup): AllMoviesItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)

            return AllMoviesItemViewHolder(
                AllMoviesListItemBinding.inflate(layoutInflater, parent, false)
            )
        }

        fun from(parent: ViewGroup): AllMoviesItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)

            return AllMoviesItemViewHolder(
                AllMoviesListItemBinding.inflate(layoutInflater, parent, false)
            )
        }

        interface AllMoviesItemViewHolderClickListener {

            fun onMovieClick(movie: Movie)

            fun onBookmarkClick(movie: Movie)

        }

    }

}