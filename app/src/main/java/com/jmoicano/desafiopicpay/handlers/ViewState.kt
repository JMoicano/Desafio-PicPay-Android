package com.jmoicano.desafiopicpay.handlers

sealed class ViewState {

    object Loading : ViewState()

    object Success : ViewState()

    data class Error(val message: String?) : ViewState()
}