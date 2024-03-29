package by.bashlikovvv.morescreen.presentation.ui.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import by.bashlikovvv.core.domain.model.Movie

class MoviesListAdapter(
    private val onClickListener: MoviesListAdapterClickListener
) : PagingDataAdapter<Movie, MovieItemViewHolder>(MovieDiffCallback) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemViewHolder {
        return MovieItemViewHolder(parent)
    }

    override fun onBindViewHolder(holder: MovieItemViewHolder, position: Int) {
        val item = getItem(position) ?: return
        holder.bind(
            item = item,
            movieItemViewHolderClickListener = object : MovieItemViewHolder.MovieItemViewHolderClickListener {
                override fun onMovieClicked(movie: Movie) {
                    onClickListener.notifyMovieClicked(movie)
                }
            }
        )
    }

    interface MoviesListAdapterClickListener {

        fun notifyMovieClicked(movie: Movie)

    }

}