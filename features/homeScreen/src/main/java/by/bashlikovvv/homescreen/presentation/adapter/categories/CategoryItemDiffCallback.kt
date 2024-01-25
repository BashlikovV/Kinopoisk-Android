package by.bashlikovvv.homescreen.presentation.adapter.categories

import androidx.recyclerview.widget.DiffUtil
import by.bashlikovvv.homescreen.domain.model.Category

object CategoryItemDiffCallback : DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem == newItem
    }
}