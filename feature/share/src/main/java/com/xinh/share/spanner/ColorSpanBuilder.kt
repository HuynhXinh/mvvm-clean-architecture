package com.xinh.share.spanner

import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import androidx.annotation.ColorInt

class ColorSpanBuilder(private val type: Int, @ColorInt private val color: Int) : SpanBuilder {
    override fun build(): Any {
        return when (type) {
            FOREGROUND -> ForegroundColorSpan(color)
            else -> BackgroundColorSpan(color)
        }
    }

    companion object {
        internal const val FOREGROUND = 0
        internal const val BACKGROUND = 1
    }
}