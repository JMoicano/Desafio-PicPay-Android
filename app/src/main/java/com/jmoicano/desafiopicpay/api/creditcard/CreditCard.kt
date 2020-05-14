package com.jmoicano.desafiopicpay.api.creditcard

import java.util.*

data class CreditCard(
    val number: String,
    val name: String,
    val dueDate: Date,
    val cvv: String
)