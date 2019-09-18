package com.xinh.share.widget.common

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import com.xinh.share.R
import com.xinh.share.extension.convertDpToPixel

interface ViewComponent {
    fun getView(): View

    fun getLinearLayoutParams(context: Context): LinearLayout.LayoutParams {
        val marginTop = context.convertDpToPixel(R.dimen.margin_16dp)
        return LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(0, marginTop, 0, 0)
        }
    }
}