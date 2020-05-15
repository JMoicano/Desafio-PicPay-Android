package com.jmoicano.desafiopicpay.api.payment.models

import android.os.Parcelable
import com.jmoicano.desafiopicpay.api.user.models.User
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal

@Parcelize
data class Transaction(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "timestamp") val timestamp: Long,
    @field:Json(name = "value") val value: BigDecimal,
    @field:Json(name = "destination_user") val destination_user: User,
    @field:Json(name = "success") val success: Boolean,
    @field:Json(name = "status") val status: String
) : Parcelable