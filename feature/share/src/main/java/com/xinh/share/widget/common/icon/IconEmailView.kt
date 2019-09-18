package com.xinh.share.widget.common.icon

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.xinh.share.R
import com.xinh.share.widget.common.BaseLinearLayout
import com.xinh.share.widget.common.ViewComponent

class IconEmailView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BaseLinearLayout(context, attrs, defStyleAttr), ViewComponent {

    override fun getLayoutId(): Int {
        return R.layout.view_icon_email
    }

    override fun onViewCreated() {
    }

    override fun getView(): View {
        return this
    }
}