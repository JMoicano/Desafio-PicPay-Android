package com.jmoicano.desafiopicpay.api.user.models

import com.squareup.moshi.Json

data class User(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "img") val img: String,
    @field:Json(name = "username") val username: String
)