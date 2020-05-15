package com.jmoicano.desafiopicpay.app.payment.repositories

import com.jmoicano.desafiopicpay.api.CoroutineContextProvider
import com.jmoicano.desafiopicpay.api.creditcard.models.CreditCard
import com.jmoicano.desafiopicpay.api.model.Resource
import com.jmoicano.desafiopicpay.api.payment.PaymentApi
import com.jmoicano.desafiopicpay.api.payment.models.Payment
import com.jmoicano.desafiopicpay.api.payment.models.Transaction
import kotlinx.coroutines.withContext
import java.io.IOException
import java.math.BigDecimal

class PaymentRepository(
    private val paymentApi: PaymentApi,
    private val coroutineContextProvider: CoroutineContextProvider
) {
    suspend fun pay(
        creditCard: CreditCard,
        destinationUserId: Int,
        value: BigDecimal
    ): Resource<Transaction?> =
        try {
            withContext(coroutineContextProvider.io) {
                val response = paymentApi.postTransactionAsync(
                    Payment(
                        number = creditCard.number.replace( " ", ""),
                        cvv = creditCard.cvv,
                        value =  value,
                        dueDate = creditCard.dueDate,
                        destinationUserId = destinationUserId
                    )
                ).await()

                if (response.isSuccessful) {
                    if (response.body()?.transaction?.success == true) {
                        Resource.Success(response.body()?.transaction)
                    } else {
                        Resource.Failure<Transaction?>(response.body()?.transaction?.status)
                    }
                } else {
                    Resource.Failure<Transaction?>(response.errorBody().toString())
                }
            }
        }catch (e: IOException){
            Resource.Failure(e.message)
        }
}