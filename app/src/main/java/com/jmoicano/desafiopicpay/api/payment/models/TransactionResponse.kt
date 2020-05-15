package com.jmoicano.desafiopicpay.api.payment.models

import com.squareup.moshi.Json

data class TransactionResponse(
    @field:Json(name = "transaction") val transaction: Transaction
)