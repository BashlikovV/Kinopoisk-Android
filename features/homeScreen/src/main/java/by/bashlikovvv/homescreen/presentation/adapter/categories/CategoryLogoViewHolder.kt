package by.bashlikovvv.homescreen.presentation.adapter.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.bashlikovvv.homescreen.databinding.ItemCategoryLogoBinding
import by.bashlikovvv.homescreen.domain.model.CategoryLogo

class CategoryLogoViewHolder(
    private val binding: ItemCategoryLogoBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CategoryLogo) {
        binding.categoryImage.setImageResource(item.imageId)
    }

    companion object {

        operator fun invoke(parent: ViewGroup): CategoryLogoViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)

            return CategoryLogoViewHolder(
                ItemCategoryLogoBinding.inflate(layoutInflater, parent, false)
            )
        }

    }

}