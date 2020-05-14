package com.jmoicano.desafiopicpay.app.creditcard

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jmoicano.desafiopicpay.api.creditcard.CreditCard
import java.util.*

class EditCreditCardViewModel : ViewModel() {

    val buttonVisible = MediatorLiveData<Boolean>()

    val cardNumber = MutableLiveData<String>()

    val cardName = MutableLiveData<String>()

    val date = MutableLiveData<Date>()

    val cvv = MutableLiveData<String>()

    init {
        buttonVisible.addSource(cardNumber) {
            buttonVisible.value = checkButtonVisibility()
        }
        buttonVisible.addSource(cardName) {
            buttonVisible.value = checkButtonVisibility()
        }
        buttonVisible.addSource(date) {
            buttonVisible.value = checkButtonVisibility()
        }
        buttonVisible.addSource(cvv) {
            buttonVisible.value = checkButtonVisibility()
        }
    }

    private fun checkButtonVisibility(): Boolean =
        !cardNumber.value.isNullOrBlank() &&
                !cardName.value.isNullOrBlank() &&
                !cvv.value.isNullOrBlank() &&
                date.value != null

    fun getCreditCard() : CreditCard =
        CreditCard(
            cardNumber.value.orEmpty(),
            cardName.value.orEmpty(),
            date.value?: Date(),
            cvv.value.orEmpty()
        )

    fun setCreditCard(creditCard: CreditCard) {
        creditCard.run {
            cardNumber.postValue(number)
            cardName.postValue(name)
            date.postValue(dueDate)
            this@EditCreditCardViewModel.cvv.postValue(cvv)
        }
    }
}