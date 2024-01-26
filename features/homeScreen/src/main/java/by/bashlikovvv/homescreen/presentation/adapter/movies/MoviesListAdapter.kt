package by.bashlikovvv.homescreen.presentation.adapter.movies

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import by.bashlikovvv.core.domain.model.Movie
import by.bashlikovvv.homescreen.domain.model.CategoryMore
import by.bashlikovvv.homescreen.domain.model.CategoryMovie
import by.bashlikovvv.homescreen.domain.model.CategoryTitle
import by.bashlikovvv.homescreen.domain.model.MoviesCategory

class MoviesListAdapter(
    private val notifyMovieClicked: (Movie) -> Unit,
    private val notifyMoreClicked: (CategoryMore) -> Unit
) : ListAdapter<MoviesCategory, RecyclerView.ViewHolder>(MoviesCategoryDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            TYPE_TITLE -> TitleItemViewHolder(parent)
            TYPE_MOVIE -> MovieItemViewHolder(parent)
            TYPE_MORE -> MoreItemViewHolder(parent)
            else -> throw IllegalStateException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when(holder) {
            is TitleItemViewHolder -> holder.bind(item as CategoryTitle)
            is MovieItemViewHolder -> holder.bind(item as CategoryMovie) { notifyMovieClicked(it) }
            is MoreItemViewHolder -> holder.bind(item as CategoryMore) { notifyMoreClicked(it) }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)) {
            is CategoryMovie -> TYPE_MOVIE
            is CategoryTitle -> TYPE_TITLE
            is CategoryMore -> TYPE_MORE
            else -> throw IllegalStateException()
        }
    }

    fun getCategoryPosition(category: CategoryTitle): Int {
        for (i in currentList.indices) {
            if ((currentList[i] as CategoryTitle).itemText == category.itemText) {
                return i
            }
        }

        return 0
    }

    companion object {
        const val TYPE_TITLE = 0
        const val TYPE_MOVIE = 1
        const val TYPE_MORE = 2
    }

}