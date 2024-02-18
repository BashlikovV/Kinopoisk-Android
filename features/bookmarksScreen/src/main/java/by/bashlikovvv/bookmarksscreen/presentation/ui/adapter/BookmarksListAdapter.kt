package by.bashlikovvv.bookmarksscreen.presentation.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import by.bashlikovvv.core.domain.model.Movie

class BookmarksListAdapter(
    private val onCLickListener: BookmarksListAdapterClickListener
): ListAdapter<Movie, BookmarksListItemViewHolder>(BookmarksListItemDiffCallback) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarksListItemViewHolder {
        return BookmarksListItemViewHolder(parent)
    }

    override fun onBindViewHolder(holder: BookmarksListItemViewHolder, position: Int) {
        holder.bind(
            item = getItem(position),
            onCLickListener = object : BookmarksListItemViewHolder.BookmarksListItemViewHolderClickListener {
                override fun onBookmarkClicked(movie: Movie) {
                    onCLickListener.notifyBookmarkClicked(movie)
                }

                override fun onRemoveBookmarkClicked(movie: Movie) {
                    onCLickListener.notifyDeleteBookmarkClicked(movie)
                }
            }
        )
    }

    interface BookmarksListAdapterClickListener {

        fun notifyBookmarkClicked(movie: Movie)

        fun notifyDeleteBookmarkClicked(movie: Movie)

    }

}