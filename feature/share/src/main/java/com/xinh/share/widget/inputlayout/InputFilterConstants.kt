package com.xinh.share.widget.inputlayout

import android.text.InputFilter
import android.text.TextUtils

object InputFilterConstants {
    private const val EMPTY = ""

    val SPACE_INPUT_FILTER = InputFilter { source, start, end, _, _, _ ->
        for (i in start until end) {
            val currentChar = source[i]
            if (Character.isWhitespace(currentChar)) {
                return@InputFilter EMPTY
            }
        }
        null
    }

    var SPECIAL_INPUT_FILTER = InputFilter { source, _, _, _, _, _ ->
        val blockCharacterSet = "~#^|$%&*!@+£,€?:/%"
        if (source != null && blockCharacterSet.contains("" + source) || TextUtils.isDigitsOnly(source)) {
            return@InputFilter EMPTY
        }
        null
    }
}