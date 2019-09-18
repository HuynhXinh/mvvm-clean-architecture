package com.xinh.share.extension

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator

private const val START_DELAY = 300L
private const val DURATION = 2000L
private val INTERPOLATOR_IN = LinearOutSlowInInterpolator()

fun View.fadeIn() {
    fade(value = 1f)
}

fun View.fadeOut() {
    fade(value = 0f)
}

fun View.fade(
    value: Float,
    startDelay: Long = START_DELAY,
    duration: Long = DURATION,
    onAnimationStart: () -> Unit = {},
    onAnimationEnd: () -> Unit = {}
) {
    animate()
        .setStartDelay(startDelay)
        .alpha(value)
        .setDuration(duration)
        .setInterpolator(INTERPOLATOR_IN).withLayer()
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                onAnimationStart()
            }

            override fun onAnimationEnd(animation: Animator?) {
                onAnimationEnd()
            }
        })
}