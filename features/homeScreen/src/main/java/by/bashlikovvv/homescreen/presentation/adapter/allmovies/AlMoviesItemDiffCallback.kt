package by.bashlikovvv.homescreen.presentation.adapter.allmovies

import androidx.recyclerview.widget.DiffUtil
import by.bashlikovvv.core.domain.model.Movie

object AlMoviesItemDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}