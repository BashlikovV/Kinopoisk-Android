package by.bashlikovvv.core.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import by.bashlikovvv.core.databinding.ItemProgressLayoutBinding

class ItemProgressViewHolder(
    binding: ItemProgressLayoutBinding
) : ItemViewHolder(binding.root) {
    override fun bind(loadState: LoadState) {  }

    companion object {

        operator fun invoke(
            parent: ViewGroup
        ): ItemProgressViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)

            return ItemProgressViewHolder(
                ItemProgressLayoutBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
            )
        }

    }
}