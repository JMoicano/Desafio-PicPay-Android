package com.jmoicano.desafiopicpay.api.user

import com.jmoicano.desafiopicpay.api.user.models.User
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface ContactApi {
    @GET("/tests/mobdev/users")
    fun getUsersAsync():Deferred<Response<List<User>>>
}