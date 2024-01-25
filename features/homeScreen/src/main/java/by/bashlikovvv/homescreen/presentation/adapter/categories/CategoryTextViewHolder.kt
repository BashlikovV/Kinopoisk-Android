package by.bashlikovvv.homescreen.presentation.adapter.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.allViews
import androidx.recyclerview.widget.RecyclerView
import by.bashlikovvv.homescreen.databinding.ItemCategoryTextBinding
import by.bashlikovvv.homescreen.domain.model.CategoryText

class CategoryTextViewHolder(
    private val binding: ItemCategoryTextBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CategoryText, selected: Boolean, onClick: (CategoryText) -> Unit) {
        binding.categoryText.setText(item.itemText)
        if (selected) {
            binding.root.allViews.forEach {
                it.isSelected = true
            }
        } else {
            binding.root.allViews.forEach {
                it.isSelected = false
            }
        }

        binding.root.setOnClickListener { onClick(item) }
    }

    companion object {

        fun from(parent: ViewGroup): CategoryTextViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)

            return CategoryTextViewHolder(
                ItemCategoryTextBinding.inflate(layoutInflater, parent, false)
            )
        }

    }

}