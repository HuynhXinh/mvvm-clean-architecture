package com.xinh.share.widget.buttonview

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.xinh.share.R
import com.xinh.share.extension.convertDpToPixel
import com.xinh.share.extension.getString

class ButtonSignInView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : GreenButtonView(context, attrs, defStyleAttr) {
    override fun onViewCreated() {
        super.onViewCreated()

        setText(getString(R.string.txt_sign_in))
    }


    override fun getLinearLayoutParams(context: Context): LayoutParams {
        val marginTop = context.convertDpToPixel(R.dimen.margin_25dp)
        return LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(0, marginTop, 0, 0)
        }
    }
}