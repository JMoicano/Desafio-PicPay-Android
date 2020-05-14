package com.jmoicano.desafiopicpay.api.creditcard.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class CreditCard(
    val number: String,
    val name: String,
    val dueDate: Date,
    val cvv: String
) : Parcelable