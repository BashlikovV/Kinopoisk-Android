package by.bashlikovvv.homescreen.presentation.adapter.allmovies

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import by.bashlikovvv.core.domain.model.Movie

class AllMoviesListAdapter(
    private val notifyMovieClicked: (Movie) -> Unit
) : PagingDataAdapter<Movie, AllMoviesItemViewHolder>(AlMoviesItemDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllMoviesItemViewHolder {
        return AllMoviesItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: AllMoviesItemViewHolder, position: Int) {
        holder.bind(getItem(position) ?: return) {
            notifyMovieClicked(getItem(position) ?: return@bind)
        }
    }

}