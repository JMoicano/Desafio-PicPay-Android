package com.jmoicano.desafiopicpay.api.payment

import com.jmoicano.desafiopicpay.api.payment.models.Payment
import com.jmoicano.desafiopicpay.api.payment.models.Transaction
import com.jmoicano.desafiopicpay.api.payment.models.TransactionResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PaymentApi {
    @POST("/tests/mobdev/transaction ")
    fun postTransactionAsync(@Body payment: Payment): Deferred<Response<TransactionResponse>>
}