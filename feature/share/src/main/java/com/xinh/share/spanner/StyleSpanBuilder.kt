package com.xinh.share.spanner

import android.text.style.StyleSpan
import com.xinh.share.spanner.SpanBuilder

internal class StyleSpanBuilder(private val style: Int) : SpanBuilder {
    override fun build(): Any = StyleSpan(style)
}
