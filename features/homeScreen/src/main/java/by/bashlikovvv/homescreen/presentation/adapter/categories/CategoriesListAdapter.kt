package by.bashlikovvv.homescreen.presentation.adapter.categories

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import by.bashlikovvv.homescreen.domain.model.Category
import by.bashlikovvv.homescreen.domain.model.CategoryLogo
import by.bashlikovvv.homescreen.domain.model.CategoryText

class CategoriesListAdapter(
    private val notifyCategoryClicked: (Category, Int) -> Unit
) : ListAdapter<Category, RecyclerView.ViewHolder>(CategoryItemDiffCallback) {

    private var selectedItemPosition = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            ViewTypes.TYPE_LOGO -> CategoryLogoViewHolder.from(parent)
            ViewTypes.TYPE_TEXT -> CategoryTextViewHolder.from(parent)
            else -> throw IllegalStateException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is CategoryLogoViewHolder -> {
                holder.bind(item = getItem(position) as CategoryLogo)
            }
            is CategoryTextViewHolder -> {
                holder.bind(
                    item = getItem(position) as CategoryText,
                    selected = position == selectedItemPosition
                ) { categoryText ->
                    onCategoryTextClicked(position, categoryText)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)) {
            is CategoryLogo -> ViewTypes.TYPE_LOGO
            is CategoryText -> ViewTypes.TYPE_TEXT
            else -> throw IllegalStateException()
        }
    }

    private fun onCategoryTextClicked(position: Int, categoryText: CategoryText) {
        val prevPosition = selectedItemPosition
        selectedItemPosition = position
        notifyItemChanged(prevPosition)
        notifyItemChanged(position)

        notifyCategoryClicked(categoryText, position)

    }

    companion object {
        object ViewTypes {
            const val TYPE_LOGO = 0
            const val TYPE_TEXT = 1
        }
    }

}