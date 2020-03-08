package com.xinh.data.repository.user.cloud

import com.google.gson.annotations.SerializedName

data class UserDto(
        @SerializedName("first_name")
        var firstName: String? = null,

        @SerializedName("last_name")
        var lastName: String? = null,

        var token: String? = null
)