package by.bashlikovvv.homescreen.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import by.bashlikovvv.homescreen.databinding.ViewBookmarkBinding

class BookmarkView : FrameLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes)

    private val binding: ViewBookmarkBinding = ViewBookmarkBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    init {
        dropProgress()
    }

    fun notifyViewCLicked() {
        if (binding.root.progress == 0f) {
            startMoveBookmark()
        } else {
            dropProgress()
        }
    }

    private fun dropProgress() {
        binding.root.transitionToStart()
    }

    fun startMoveBookmark() {
        binding.root.transitionToEnd()
    }

    fun setBookmarkClicked() {
        binding.root.progress = 1f
    }

}