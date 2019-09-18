package com.xinh.share

import android.app.Application
import android.content.pm.PackageManager
import android.util.Base64
import com.github.ajalt.timberkt.Timber
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

open class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initLog()

        if (BuildConfig.DEBUG) {
            printFacebookKeyhash()
        }
    }

    private fun initLog() {
        if (BuildConfig.DEBUG) {
            Timber.plant(timber.log.Timber.DebugTree())
        }
    }

    private fun printFacebookKeyhash() {
        // Add code to print out the key hash
        Timber.d { "package name: $packageName" }
        try {
            val info = packageManager.getPackageInfo(
                packageName,
                PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Timber.d {
                    "KeyHash: ${Base64.encodeToString(
                        md.digest(),
                        Base64.DEFAULT
                    )}"
                }
            }
        } catch (e: PackageManager.NameNotFoundException) {

        } catch (e: NoSuchAlgorithmException) {

        }
    }
}