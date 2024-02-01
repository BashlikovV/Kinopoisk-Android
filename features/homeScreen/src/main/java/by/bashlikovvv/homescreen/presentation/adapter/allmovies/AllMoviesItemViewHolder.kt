package by.bashlikovvv.homescreen.presentation.adapter.allmovies

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

    fun bind(item: Movie, onClick: (Movie) -> Unit) {
        binding.let {
            setBitmapWithGlide(item.poster, it.movieImageView)
            it.movieNameTextView.text = item.name
            it.bookamrkView.setOnClickListener { view ->
                (view as BookmarkView).notifyViewCLicked()
            }
            it.root.setOnClickListener { onClick(item) }
        }

    }

    private fun setBitmapWithGlide(url: String, view: ImageView) {
        Glide.with(view)
            .asBitmap()
            .load(url)
            .into(view)
    }

    companion object {

        fun from(parent: ViewGroup): AllMoviesItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)

            return AllMoviesItemViewHolder(
                AllMoviesListItemBinding.inflate(layoutInflater, parent, false)
            )
        }

    }

}