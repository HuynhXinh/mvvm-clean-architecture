package com.xinh.share.spanner

open class Span(private val builder: SpanBuilder) {
    fun buildSpan() = builder.build()
}
