package by.bashlikovvv.morescreen.presentation.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import by.bashlikovvv.core.domain.model.Movie

object MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}