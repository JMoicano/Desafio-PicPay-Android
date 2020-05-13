package com.jmoicano.desafiopicpay.api

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

interface CoroutineContextProvider {
    val main: CoroutineContext
    val io: CoroutineContext

    object Impl : CoroutineContextProvider {
        override val main: CoroutineContext by lazy { Dispatchers.Main }
        override val io: CoroutineContext by lazy { Dispatchers.IO }
    }

}