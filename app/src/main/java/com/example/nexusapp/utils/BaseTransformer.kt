package com.example.nexusapp.utils

import android.view.View
import androidx.viewpager2.widget.ViewPager2

abstract class BaseTransformer : ViewPager2.PageTransformer {

    protected abstract fun onTransform(view: View, position: Float)
    override fun transformPage(view: View, position: Float) {
        onPreTransform(view, position)
        onTransform(view, position)
        onPostTransform(view, position)
    }

    private fun hideOffscreenPages(): Boolean {
        return true
    }

    private val isPagingEnabled: Boolean
        get() = false

    private fun onPreTransform(view: View, position: Float) {
        val width = view.width.toFloat()
        view.rotationX = 0f
        view.rotationY = 0f
        view.rotation = 0f
        view.scaleX = 1f
        view.scaleY = 1f
        view.pivotX = 0f
        view.pivotY = 0f
        view.translationY = 0f
        view.translationX = if (isPagingEnabled) 0f else -width * position
        if (hideOffscreenPages()) {
            view.alpha = if (position <= -1f || position >= 1f) 0f else 1f
        } else {
            view.alpha = 1f
        }
    }

    private fun onPostTransform(view: View?, position: Float) {}
}
