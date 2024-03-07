package by.bashlikovvv.core.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import by.bashlikovvv.core.databinding.ItemErrorLayoutBinding

class ItemErrorViewHolder (
    private val binding: ItemErrorLayoutBinding
) : ItemViewHolder(binding.root) {
    override fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.errorMessage.text = loadState.error.message ?: "error"
        }
    }

    companion object {

        operator fun invoke(
            parent: ViewGroup
        ): ItemErrorViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)

            return ItemErrorViewHolder(
                ItemErrorLayoutBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
            )
        }
        
    }

}