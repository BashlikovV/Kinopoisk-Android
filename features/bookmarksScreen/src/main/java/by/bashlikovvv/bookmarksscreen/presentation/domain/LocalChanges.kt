package by.bashlikovvv.bookmarksscreen.presentation.domain

class LocalChanges {

    private val idsInProgress = mutableSetOf<Long>()
    private val selectedFlags = mutableMapOf<Long, Boolean>()

    fun selectItem(id: Long) {
        selectedFlags.merge(id, selectedFlags[id] ?: true) { _, _ ->
            selectedFlags[id]?.not() ?: true
        }
    }

    fun isSelected(id: Long) = selectedFlags[id]

    fun unselect() = selectedFlags.clear()

    fun isInProgress(id: Long): Boolean = idsInProgress.contains(id)

    fun isContainsSelected() = selectedFlags.containsValue(true)
}