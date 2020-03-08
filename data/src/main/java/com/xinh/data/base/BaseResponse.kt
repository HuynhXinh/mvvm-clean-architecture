package com.xinh.data.base

import com.xinh.domain.exception.Error

data class BaseResponse<D>(
        var data: D? = null,
        var status: Boolean? = null,
        var error: Error? = null
)