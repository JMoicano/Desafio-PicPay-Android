package com.jmoicano.desafiopicpay.injection

import com.jmoicano.desafiopicpay.api.CoroutineContextProvider
import org.koin.dsl.module

val apiModule = module {
    single<CoroutineContextProvider> { CoroutineContextProvider.Impl }
    
}