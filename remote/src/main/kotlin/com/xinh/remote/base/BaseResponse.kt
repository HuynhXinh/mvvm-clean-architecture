package com.xinh.remote.base

import com.xinh.remote.model.Error

data class BaseResponse<D>(
    var data: D? = null,
    var status: Boolean? = null,
    var error: Error? = null
)