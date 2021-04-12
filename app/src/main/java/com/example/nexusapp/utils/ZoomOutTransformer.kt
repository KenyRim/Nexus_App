package com.example.nexusapp.utils

import android.view.View
import java.lang.Math.abs

class ZoomOutTransformer : BaseTransformer() {
    override fun onTransform(view: View, position: Float) {
        val scale = 1f + kotlin.math.abs(position)
        view.scaleX = scale
        view.scaleY = scale
        view.pivotX = view.width * 0.5f
        view.pivotY = view.height * 0.5f
        view.rotation = if (position < -1f || position > 1f) 0f else 1f - (scale - 1f)
//        if (position == -1f) {
//            view.translationX = view.width.toFloat() * -1
//        }
    }
}