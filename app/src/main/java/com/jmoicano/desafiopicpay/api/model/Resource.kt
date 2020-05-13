package com.jmoicano.desafiopicpay.api.model

sealed class Resource<T> {
    data class Success<T>(val data: T)  : Resource<T>()
    data class Failure<T>(val error: String?) : Resource<T>()
}