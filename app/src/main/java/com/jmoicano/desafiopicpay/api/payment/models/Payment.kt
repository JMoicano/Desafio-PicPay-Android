package com.jmoicano.desafiopicpay.api.payment.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal
import java.util.*

@Parcelize
data class Payment(
    @field:Json(name = "card_number") val number: String,
    @field:Json(name = "cvv") val cvv: String,
    @field:Json(name = "value") val value: BigDecimal,
    @field:Json(name = "expiry_date") val dueDate: Date,
    @field:Json(name = "destination_user_id") val destinationUserId: Int
) : Parcelable