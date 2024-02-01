package by.bashlikovvv.homescreen.presentation.adapter.allmovies

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import by.bashlikovvv.core.domain.model.Movie

class AllMoviesListAdapter(
    private val onClickListener: AllMoviesListAdapterClickListener
) : PagingDataAdapter<Movie, AllMoviesItemViewHolder>(AlMoviesItemDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllMoviesItemViewHolder {
        return AllMoviesItemViewHolder(parent)
    }

    override fun onBindViewHolder(holder: AllMoviesItemViewHolder, position: Int) {
        holder.bind(
            item = getItem(position) ?: return,
            onClickListener = object : AllMoviesItemViewHolder.Companion.AllMoviesItemViewHolderClickListener {
                override fun onMovieClick(movie: Movie) {
                    onClickListener.notifyMovieClicked(movie)
                }

                override fun onBookmarkClick(movie: Movie) {
                    onClickListener.notifyBookmarkClicked(movie)
                }
            }
        )
    }

    interface AllMoviesListAdapterClickListener {

        fun notifyMovieClicked(movie: Movie)

        fun notifyBookmarkClicked(movie: Movie)

    }

}