package com.xinh.share.spanner

import android.text.TextPaint
import android.text.style.CharacterStyle
import android.text.style.ClickableSpan
import android.view.View

class ClickSpanBuilder(private val clickListener: View.OnClickListener) : SpanBuilder {
    override fun build(): Any = EasyClickableSpan(clickListener)

    private class EasyClickableSpan(private val clickListener: View.OnClickListener) :
        ClickableSpan() {
        override fun onClick(widget: View) {
            clickListener.onClick(widget)
        }

        override fun updateDrawState(ds: TextPaint) {
            ds.isUnderlineText = false
        }
    }
}