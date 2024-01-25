package by.bashlikovvv.core.ext

import android.graphics.Point
import android.view.View

fun View.locationOnScreen(): Point {
    val viewLocation = IntArray(2)
    this.getLocationOnScreen(viewLocation)
    return Point(viewLocation[0], viewLocation[1])
}