package com.xinh.travel

import android.content.Context
import android.content.Intent
import com.xinh.account.OnLogoutSuccessListener
import com.xinh.share.BaseSimpleActivity
import com.xinh.share.NavigatorActivity
import org.koin.android.ext.android.inject

class MainActivity : BaseSimpleActivity(), OnLogoutSuccessListener {

    private val navigator: NavigatorActivity by inject()

    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun initView() {

    }

    override fun onLogoutSuccess() {
        navigator.gotoAuthentication(this)
        finish()
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }
}
