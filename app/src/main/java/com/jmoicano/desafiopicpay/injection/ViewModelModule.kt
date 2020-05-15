package com.jmoicano.desafiopicpay.injection

import com.jmoicano.desafiopicpay.app.contacts.viewmodels.ContactViewModel
import com.jmoicano.desafiopicpay.app.creditcard.viewmodels.EditCreditCardViewModel
import com.jmoicano.desafiopicpay.app.payment.viewmodels.PaymentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { ContactViewModel(get()) }
    viewModel { EditCreditCardViewModel() }
    viewModel {
        PaymentViewModel(
            get()
        )
    }
}