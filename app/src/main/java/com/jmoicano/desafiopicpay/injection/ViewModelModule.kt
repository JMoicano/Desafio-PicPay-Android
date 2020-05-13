package com.jmoicano.desafiopicpay.injection

import com.jmoicano.desafiopicpay.app.contacts.viewmodels.ContactViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { ContactViewModel(get()) }
}