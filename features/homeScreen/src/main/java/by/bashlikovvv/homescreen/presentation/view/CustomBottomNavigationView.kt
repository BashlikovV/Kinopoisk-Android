package by.bashlikovvv.homescreen.presentation.view

import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.os.Build
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.doOnPreDraw
import androidx.core.view.forEach
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import by.bashlikovvv.homescreen.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.math.abs


private const val DEFAULT_SCALE = 1f
private const val MAX_SCALE = 15f
private const val BASE_DURATION = 300L
private const val VARIABLE_DURATION = 300L

class CustomBottomNavigationView : BottomNavigationView {

    private var externalSelectedListener: OnItemSelectedListener? = null
    private var animator: ValueAnimator? = null

    private val indicator = RectF()
//    private val rects = Array(size = 4) { RectF() }
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.indicator_selected)
    }

    private val bottomOffset = 15f
    private var defaultSize = 5f
    @Suppress("DEPRECATION")
    private val diff: Int by lazy {
        var lastViewRight = 0
        menu.forEach {
            lastViewRight = maxOf(findViewById<View>(it.itemId).right, lastViewRight)
        }
        val activity = getActivity()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val bounds = activity?.windowManager?.currentWindowMetrics?.bounds ?: Rect()

            bounds.right - bounds.left - lastViewRight
        } else {
            val display = activity?.windowManager?.defaultDisplay
            val displayMetrics = DisplayMetrics()
            display?.getMetrics(displayMetrics)

            displayMetrics.widthPixels - lastViewRight
        }
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr)

    init {
        itemActiveIndicatorShapeAppearance = null
        super.setOnItemSelectedListener {
            if (externalSelectedListener?.onNavigationItemSelected(it) != false) {
                onItemSelected(it.itemId, true)

                true
            } else {
                false
            }
        }
    }

    override fun setOnItemSelectedListener(listener: OnItemSelectedListener?) {
        externalSelectedListener = listener
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        doOnPreDraw {
            onItemSelected(selectedItemId, false)
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        cancelAnimator(setEndValues = true)
    }

    override fun dispatchDraw(canvas: Canvas) {
        super.dispatchDraw(canvas)
        if (isLaidOut) {
            val cornerRadius = indicator.height() / 2f
            canvas.drawRoundRect(indicator, cornerRadius, cornerRadius, paint)
//            rects.forEach { rect ->
//                canvas.drawRect(rect, paint)
//            }
        }
    }

    private fun onItemSelected(itemId: Int, animate: Boolean = true) {
        if (!isLaidOut) return

        cancelAnimator(setEndValues = false)

//        (itemView.right - itemView.left).toFloat() / 2
        val itemView = findViewById<View>(itemId) ?: return
        defaultSize = if (itemView.left == 0) {
//            rects[0].set(
//                itemView.left.toFloat(),
//                itemView.top.toFloat(),
//                (itemView.left + 5).toFloat(),
//                itemView.bottom.toFloat()
//            )
//            rects[1].set(
//                itemView.right.toFloat() + diff / 2,
//                itemView.top.toFloat(),
//                (itemView.right - 5).toFloat() + diff / 2,
//                itemView.bottom.toFloat()
//            )

            (itemView.right.toFloat() + diff / 2) - itemView.left
        } else {
//            rects[2].set(
//                itemView.left.toFloat() + diff / 2,
//                itemView.top.toFloat(),
//                (itemView.left + 5).toFloat() + diff / 2,
//                itemView.bottom.toFloat()
//            )
//            rects[3].set(
//                itemView.right.toFloat() + diff,
//                itemView.top.toFloat(),
//                (itemView.right - 5).toFloat() + diff,
//                itemView.bottom.toFloat()
//            )

            (itemView.right + diff) - (itemView.left.toFloat() + diff / 2)
        }

        val fromCenterX = indicator.centerX()
        val fromScale = indicator.width() / defaultSize

        animator = ValueAnimator.ofFloat(fromScale, MAX_SCALE, DEFAULT_SCALE).apply {
            addUpdateListener {
                val progress = it.animatedFraction
                val distanceTravelled = linearInterpolation(progress, fromCenterX, itemView.centerX + diff / 2)

                val left = distanceTravelled - defaultSize / 4
                val top = height - bottomOffset - 10
                val right = distanceTravelled + defaultSize / 4
                val bottom = height - bottomOffset

                indicator.set(
                    left,
                    top,
                    right,
                    bottom
                )
                invalidate()
            }

            interpolator = LinearOutSlowInInterpolator()

            val distanceToMove = abs(fromCenterX - itemView.centerX)
            duration = if (animate) calculateDuration(distanceToMove) else 0L

            start()
        }
    }

    private fun linearInterpolation(t: Float, a: Float, b: Float) = (1 - t) * a + t * b

    private fun calculateDuration(distance: Float) =
        (BASE_DURATION + VARIABLE_DURATION * (distance / width).coerceIn(0f, 1f)).toLong()

    private val View.centerX get() = left + width / 2f

    private fun cancelAnimator(setEndValues: Boolean) = animator?.let {
        if (setEndValues) {
            it.end()
        } else {
            it.cancel()
        }
        it.removeAllUpdateListeners()
        animator = null
    }

    private fun getActivity(): Activity? {
        var context = context
        while (context is ContextWrapper) {
            if (context is Activity) {
                return context
            }
            context = context.baseContext
        }
        return null
    }
}