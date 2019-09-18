package com.xinh.remote.error

import com.xinh.remote.model.Error

open class BaseRemoteError(var error: Error? = null) : RuntimeException()
