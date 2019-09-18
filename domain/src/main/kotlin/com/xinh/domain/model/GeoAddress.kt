package com.xinh.domain.model

data class GeoAddress(
        var lat: Double?,
        var lng: Double?,
        var addressName: String?,
        var city: String?,
        var state: String?,
        var country: String?,
        var postalCode: String?,
        var knownName: String?
)