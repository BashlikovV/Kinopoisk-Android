package by.bashlikovvv.bookmarksscreen.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import by.bashlikovvv.bookmarksscreen.databinding.BookmarksListItemBinding
import by.bashlikovvv.core.domain.model.Movie
import com.bumptech.glide.Glide

class BookmarksListItemViewHolder(
    private val binding: BookmarksListItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Movie, onCLickListener: BookmarksListItemViewHolderClickListener) {
        binding.let {
            setBitmapWithGlide(item.poster, it.posterImageView)
            it.nameTextView.text = item.name
            it.descriptionTextView.text = item.description
            it.removeFromBookmarksTextView.setOnClickListener {
                onCLickListener.onRemoveBookmarkClicked(item)
            }
            it.root.setOnClickListener { onCLickListener.onBookmarkClicked(item) }
        }
    }

    private fun setBitmapWithGlide(url: String, view: ImageView) {
        Glide.with(view)
            .asBitmap()
            .load(url)
            .into(view)
    }

    companion object {

        operator fun invoke(parent: ViewGroup): BookmarksListItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)

            return BookmarksListItemViewHolder(
                BookmarksListItemBinding.inflate(layoutInflater, parent, false)
            )
        }

    }

    interface BookmarksListItemViewHolderClickListener {

        fun onBookmarkClicked(movie: Movie)

        fun onRemoveBookmarkClicked(movie: Movie)

    }

}