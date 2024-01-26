package by.bashlikovvv.homescreen.presentation.adapter.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.bashlikovvv.homescreen.databinding.TextListItemBinding
import by.bashlikovvv.homescreen.domain.model.CategoryTitle

class TitleItemViewHolder(
    private val binding: TextListItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CategoryTitle) {
        binding.categoryNameTextView.setText(item.itemText)
    }

    companion object {

        operator fun invoke(parent: ViewGroup): TitleItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)

            return TitleItemViewHolder(
                TextListItemBinding.inflate(
                    layoutInflater, parent, false
                )
            )
        }

    }

}