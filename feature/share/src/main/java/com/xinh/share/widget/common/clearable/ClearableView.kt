package com.xinh.share.widget.common.clearable

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.xinh.share.R
import com.xinh.share.extension.showOrGone
import com.xinh.share.widget.common.BaseLinearLayout
import kotlinx.android.synthetic.main.view_clearable.view.*

class ClearableView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseLinearLayout(context, attrs, defStyleAttr), ClearableComponent {

    private var listener: (() -> Unit)? = null

    override fun getLayoutId(): Int {
        return R.layout.view_clearable
    }

    override fun onViewCreated() {
        ivClear.setOnClickListener {
            listener?.invoke()
        }
    }

    override fun setOnClearClickListener(listener: () -> Unit) {
        this.listener = listener
    }

    override fun getView(): View {
        return this
    }
}