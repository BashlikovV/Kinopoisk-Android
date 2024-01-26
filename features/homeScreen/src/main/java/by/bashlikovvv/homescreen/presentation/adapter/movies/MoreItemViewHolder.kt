package by.bashlikovvv.homescreen.presentation.adapter.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.bashlikovvv.homescreen.databinding.MoreListItemBinding
import by.bashlikovvv.homescreen.domain.model.CategoryMore

class MoreItemViewHolder(
    private val binding: MoreListItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CategoryMore, onMoreClicked: (CategoryMore) -> Unit) {
        binding.root.setOnClickListener { onMoreClicked(item) }
    }

    companion object {

        operator fun invoke(parent: ViewGroup): MoreItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)

            return MoreItemViewHolder(
                MoreListItemBinding.inflate(
                    layoutInflater, parent, false
                )
            )
        }

    }

}