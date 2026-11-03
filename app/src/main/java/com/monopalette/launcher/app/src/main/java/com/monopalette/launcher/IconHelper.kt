package com.monopalette.launcher

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.widget.ImageView

object IconHelper {
    fun applyMonochrome(imageView: ImageView) {
        val matrix = ColorMatrix()
        matrix.setSaturation(0f)
        imageView.colorFilter = ColorMatrixColorFilter(matrix)
    }
}
