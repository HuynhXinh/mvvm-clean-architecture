package com.xinh.domain.model

data class Address @JvmOverloads constructor(
        var id: Long? = null,
        var name: String? = null,
        var lat: Double? = null,
        var lng: Double? = null
)