package com.jmoicano.desafiopicpay.app.contacts.repositories

import com.jmoicano.desafiopicpay.api.CoroutineContextProvider
import com.jmoicano.desafiopicpay.api.model.Resource
import com.jmoicano.desafiopicpay.api.user.ContactApi
import com.jmoicano.desafiopicpay.api.user.models.User
import kotlinx.coroutines.withContext
import java.io.IOException

class ContactRepository(
    private val contactApi: ContactApi,
    private val coroutineContextProvider: CoroutineContextProvider
) {
    suspend fun getUsers(): Resource<List<User>?> =
        try {
            withContext(coroutineContextProvider.io){

                val response = contactApi.getUsersAsync().await()
                if (response.isSuccessful)
                    Resource.Success(response.body())
                else
                    Resource.Failure<List<User>?>(response.errorBody()?.string())
            }
        } catch (e: IOException) {
            Resource.Failure(e.message)
        }
}