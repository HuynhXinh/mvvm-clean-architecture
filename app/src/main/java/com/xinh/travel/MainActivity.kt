package com.xinh.travel

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.xinh.account.OnLogoutSuccessListener
import com.xinh.permission.PermissionEnum
import com.xinh.permission.PermissionManager
import com.xinh.permission.SimpleCallback
import com.xinh.share.BaseSimpleActivity
import com.xinh.share.NavigatorActivity
import com.xinh.workermanager.DetectLocation
import org.koin.android.ext.android.inject

class MainActivity : BaseSimpleActivity(), OnLogoutSuccessListener {

    private val navigator: NavigatorActivity by inject()

    private val detectLocation: DetectLocation by inject()

    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        PermissionManager.builder()
                .permission(PermissionEnum.ACCESS_COARSE_LOCATION, PermissionEnum.ACCESS_FINE_LOCATION)
                .callback(object : SimpleCallback {
                    override fun result(allPermissionsGranted: Boolean) {
                        if (allPermissionsGranted) {
                            detectLocation.startDetectLocation()
                        } else {
                            Toast.makeText(this@MainActivity, "Location permission denied...", Toast.LENGTH_SHORT).show()
                        }
                    }
                })
                .ask(this)
    }

    override fun onLogoutSuccess() {
        navigator.gotoAuthentication(this)
        finish()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        
        PermissionManager.handleResult(this, requestCode, permissions, grantResults)
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }
}
