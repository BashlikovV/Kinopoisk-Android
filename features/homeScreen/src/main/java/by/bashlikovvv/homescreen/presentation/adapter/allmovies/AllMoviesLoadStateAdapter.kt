package by.bashlikovvv.homescreen.presentation.adapter.allmovies

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import by.bashlikovvv.core.base.ItemErrorViewHolder
import by.bashlikovvv.core.base.ItemProgressViewHolder
import by.bashlikovvv.core.base.ItemViewHolder

class AllMoviesLoadStateAdapter : LoadStateAdapter<ItemViewHolder>() {
    override fun onBindViewHolder(holder: ItemViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ItemViewHolder {
        return when(loadState) {
            is LoadState.Loading -> ItemProgressViewHolder(parent)
            is LoadState.Error -> ItemErrorViewHolder(parent)
            is LoadState.NotLoading -> error("Not supported")
        }
    }

    override fun getStateViewType(loadState: LoadState): Int {
        return when(loadState) {
            is LoadState.Loading -> STATE_PROGRESS
            is LoadState.Error -> STATE_ERROR
            is LoadState.NotLoading -> error("not supported")
        }
    }

    private companion object {
        private const val STATE_ERROR = 1
        private const val STATE_PROGRESS = 0
    }

}