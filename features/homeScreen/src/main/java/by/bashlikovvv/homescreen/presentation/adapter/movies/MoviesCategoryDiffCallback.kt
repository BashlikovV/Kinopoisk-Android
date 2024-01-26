package by.bashlikovvv.homescreen.presentation.adapter.movies

import androidx.recyclerview.widget.DiffUtil
import by.bashlikovvv.homescreen.domain.model.MoviesCategory

object MoviesCategoryDiffCallback : DiffUtil.ItemCallback<MoviesCategory>() {
    override fun areItemsTheSame(oldItem: MoviesCategory, newItem: MoviesCategory): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: MoviesCategory, newItem: MoviesCategory): Boolean {
        return oldItem == newItem
    }
}