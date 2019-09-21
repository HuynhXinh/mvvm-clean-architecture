/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Raphaël Bussa
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.xinh.permission

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import java.util.*

/**
 * Created by raphaelbussa on 22/06/16.
 */
class PermissionManager {

    private var fullCallback: FullCallback? = null
    private var simpleCallback: SimpleCallback? = null
    private var askAgainCallback: AskAgainCallback? = null
    private var smartCallback: SmartCallback? = null

    private var askAgain = false

    private var permissions: ArrayList<PermissionEnum>? = null
    private var permissionsGranted: ArrayList<PermissionEnum>? = null
    private var permissionsDenied: ArrayList<PermissionEnum>? = null
    private var permissionsDeniedForever: ArrayList<PermissionEnum>? = null
    private var permissionToAsk: ArrayList<PermissionEnum>? = null

    private var key = PermissionConstant.KEY_PERMISSION

    /**
     * @param permissions an array of permission that you need to ask
     * @return current instance
     */
    fun permissions(permissions: ArrayList<PermissionEnum>): PermissionManager {
        this.permissions = ArrayList()
        this.permissions?.addAll(permissions)
        return this
    }

    /**
     * @param permission permission you need to ask
     * @return current instance
     */
    fun permission(permission: PermissionEnum): PermissionManager {
        this.permissions = ArrayList()
        this.permissions?.add(permission)
        return this
    }

    /**
     * @param permissions permission you need to ask
     * @return current instance
     */
    fun permission(vararg permissions: PermissionEnum): PermissionManager {
        this.permissions = ArrayList()
        Collections.addAll(this.permissions, *permissions)
        return this
    }

    /**
     * @param askAgain ask again when permission not granted
     * @return current instance
     */
    fun askAgain(askAgain: Boolean): PermissionManager {
        this.askAgain = askAgain
        return this
    }

    /**
     * @param fullCallback set fullCallback for the request
     * @return current instance
     */
    fun callback(fullCallback: FullCallback): PermissionManager {
        this.simpleCallback = null
        this.smartCallback = null
        this.fullCallback = fullCallback
        return this
    }

    /**
     * @param simpleCallback set simpleCallback for the request
     * @return current instance
     */
    fun callback(simpleCallback: SimpleCallback): PermissionManager {
        this.fullCallback = null
        this.smartCallback = null
        this.simpleCallback = simpleCallback
        return this
    }

    /**
     * @param smartCallback set smartCallback for the request
     * @return current instance
     */
    fun callback(smartCallback: SmartCallback): PermissionManager {
        this.fullCallback = null
        this.simpleCallback = null
        this.smartCallback = smartCallback
        return this
    }

    /**
     * @param askAgainCallback set askAgainCallback for the request
     * @return current instance
     */
    fun askAgainCallback(askAgainCallback: AskAgainCallback): PermissionManager {
        this.askAgainCallback = askAgainCallback
        return this
    }

    fun ask(activity: Activity) {
        ask(context = activity)
    }

    private fun ask(context: Context) {
        initArray()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val permissionToAsk = permissionToAsk(context)
            if (permissionToAsk.isEmpty()) {
                showResult()
            } else {
                when (context) {
                    is Activity -> context.requestPermissionsExt(permissionToAsk, key)

                    is Fragment -> context.requestPermissionsExt(permissionToAsk, key)
                }
            }
        } else {
            permissionsGranted?.addAll(permissions ?: emptyList())
            showResult()
        }
    }

    /**
     * @return permission that you really need to ask
     */
    private fun permissionToAsk(context: Context): Array<String> {
        val permissionToAsk = ArrayList<String>()

        permissions?.forEach {
            val isGranted: Boolean = PermissionUtils.isGranted(context, it)

            if (!isGranted) {
                permissionToAsk.add(it.toString())
            } else {
                permissionsGranted?.add(it)
            }
        }
        return permissionToAsk.toTypedArray()
    }

    /**
     * init permissions ArrayList
     */
    private fun initArray() {
        this.permissionsGranted = ArrayList()
        this.permissionsDenied = ArrayList()
        this.permissionsDeniedForever = ArrayList()
        this.permissionToAsk = ArrayList()
    }

    /**
     * check if one of three types of callback are not null and pass data
     */
    private fun showResult() {
        if (simpleCallback != null)
            simpleCallback?.result(areAllPermissionGranted())
        if (fullCallback != null)
            fullCallback?.result(
                    permissionsGranted,
                    permissionsDenied,
                    permissionsDeniedForever,
                    permissions
            )
        if (smartCallback != null)
            smartCallback?.result(
                    areAllPermissionGranted(),
                    !permissionsDeniedForever.isNullOrEmpty()
            )
        instance = null
    }

    private fun areAllPermissionGranted(): Boolean {
        return permissionToAsk?.size == 0 || permissionToAsk?.size == permissionsGranted?.size
    }

    companion object {

        /**
         * @return current instance
         */
        @Volatile
        private var instance: PermissionManager? = null

        fun builder(): PermissionManager {
            return instance ?: synchronized(this) {
                PermissionManager().also { instance = it }
            }
        }

        fun handleResult(
                context: Context,
                requestCode: Int,
                permissions: Array<out String>,
                grantResults: IntArray
        ) {

            if (requestCode == builder().key) {

                permissions.indices.forEach { i ->
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        builder().permissionsGranted?.add(
                                PermissionEnum.fromManifestPermission(
                                        permissions[i]
                                )
                        )
                    } else {

                        if (!isPermissionsDeniedForever(context, permissions[i])) {
                            builder().permissionsDeniedForever?.add(
                                    PermissionEnum.fromManifestPermission(
                                            permissions[i]
                                    )
                            )
                        }

                        builder().permissionsDenied?.add(
                                PermissionEnum.fromManifestPermission(
                                        permissions[i]
                                )
                        )
                        builder().permissionToAsk?.add(
                                PermissionEnum.fromManifestPermission(
                                        permissions[i]
                                )
                        )
                    }
                }
                if (builder().permissionToAsk?.size != 0 && builder().askAgain) {
                    builder().askAgain = false

                    if (builder().askAgainCallback != null &&
                            builder().permissionsDeniedForever?.size != builder().permissionsDenied?.size
                    ) {

                        builder().askAgainCallback?.showRequestPermission(object :
                                AskAgainCallback.UserResponse {
                            override fun result(askAgain: Boolean) {
                                if (askAgain) {
                                    builder().ask(context)
                                } else {
                                    builder().showResult()
                                }
                            }
                        })
                    } else {
                        builder().ask(context)
                    }
                } else {
                    builder().showResult()
                }
            }
        }

        private fun isPermissionsDeniedForever(context: Context, permission: String): Boolean {
            return when (context) {
                is Activity -> ActivityCompat.shouldShowRequestPermissionRationale(
                        context,
                        permission
                )

                is Fragment -> context.shouldShowRequestPermissionRationale(permission)

                else -> false
            }
        }
    }

}