package com.xinh.share.spanner

import android.text.Layout
import android.text.style.AlignmentSpan

class AlignmentSpanBuilder(private val alignment: Layout.Alignment) : SpanBuilder {
    override fun build(): Any = AlignmentSpan.Standard(alignment)
}