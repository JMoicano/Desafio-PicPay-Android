package com.jmoicano.desafiopicpay.injection

import com.jmoicano.desafiopicpay.app.contacts.repositories.ContactRepository
import org.koin.dsl.module

val repositoryModule = module {
    single {
        ContactRepository(
            get(),
            get()
        )
    }
}