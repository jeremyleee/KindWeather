package com.tragicfruit.kindweather.utils

import android.graphics.Outline
import android.graphics.Rect
import android.view.View
import android.view.ViewOutlineProvider

/**
 * Allows for shadows to be drawn for rounded rectangles
 * https://github.com/rock3r/uplift
 */
class RoundRectOutlineProvider(
    val cornerRadius: Float = 0f,
    var scaleX: Float = 1f,
    var scaleY: Float = 1f,
    var yShift: Int = 0
) : ViewOutlineProvider() {

    private val rect: Rect = Rect()

    override fun getOutline(view: View?, outline: Outline?) {
        view?.background?.copyBounds(rect)
        rect.scale(scaleX, scaleY)
        rect.offset(0, yShift)
        outline?.setRoundRect(rect, cornerRadius)
    }
}

private fun Rect.scale(
    scaleX: Float,
    scaleY: Float
) {
    val newWidth = width() * scaleX
    val newHeight = height() * scaleY
    val deltaX = (width() - newWidth) / 2
    val deltaY = (height() - newHeight) / 2

    set((left + deltaX).toInt(), (top + deltaY).toInt(), (right - deltaX).toInt(), (bottom - deltaY).toInt())
}