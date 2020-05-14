package com.jmoicano.desafiopicpay.api.user.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "img") val img: String,
    @field:Json(name = "username") val username: String
) : Parcelable