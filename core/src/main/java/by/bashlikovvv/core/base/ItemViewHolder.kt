package by.bashlikovvv.core.base

import android.view.View
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView

abstract class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(loadState: LoadState)
}