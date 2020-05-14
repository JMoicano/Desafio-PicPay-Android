package com.jmoicano.desafiopicpay.app.payment

import androidx.lifecycle.*
import com.jmoicano.desafiopicpay.api.creditcard.models.CreditCard
import com.jmoicano.desafiopicpay.api.model.Resource
import com.jmoicano.desafiopicpay.api.payment.models.Transaction
import com.jmoicano.desafiopicpay.api.user.models.User
import com.jmoicano.desafiopicpay.handlers.ViewState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.math.BigDecimal

class PaymentViewModel(private val paymentRepository: PaymentRepository) : ViewModel() {

    private val viewStateMutable = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState>
        get() = viewStateMutable

    private val valueFilledMutable = MediatorLiveData<Boolean>()
    val valueFilled: LiveData<Boolean>
        get() = valueFilledMutable

    private val transactionMutable = MutableLiveData<Transaction>()
    val transaction: LiveData<Transaction>
        get() = transactionMutable

    val value = MutableLiveData<String>()

    private val creditCardMutable = MutableLiveData<CreditCard>()
    val creditCard: LiveData<CreditCard>
        get() = creditCardMutable

    private val destinationUserMutable = MutableLiveData<User>()
    val destinationUser: LiveData<User>
        get() = destinationUserMutable

    private var job: Job? = null

    init {
        valueFilledMutable.addSource(value) {
            val filled =
                it.isNotEmpty() &&
                    BigDecimal(it.replace(",", ".")) != BigDecimal.ZERO
            valueFilledMutable.postValue(
                filled
            )
        }
    }

    fun setCreditCard(creditCard: CreditCard) {
        creditCardMutable.postValue(creditCard)
    }

    fun setDestinationUser(user: User) {
        destinationUserMutable.postValue(user)
    }

    fun pay() {
        if (job != null) {
            job?.cancel()
        }
        if (creditCardMutable.value != null && value.value != null && destinationUserMutable.value != null) {
            job = viewModelScope.launch {
                viewStateMutable.postValue(ViewState.Loading)
                val bigDecimalValue = BigDecimal(value.value)
                val destinationUserId = destinationUser.value?.id ?: 0
                val result = creditCardMutable.value?.let {
                    paymentRepository.pay(
                        it,
                        destinationUserId,
                        bigDecimalValue
                    )
                }
                handleResult(result)
            }
        }
    }

    private fun handleResult(resource: Resource<Transaction?>?) {
        when (resource) {
            is Resource.Success -> {
                handleSuccess(resource.data)
            }
            is Resource.Failure -> {
                handleFailure(resource.error)
            }
            else -> {
                handleFailure("Erro desconhecido")
            }
        }
    }

    private fun handleSuccess(users: Transaction?) {
        viewStateMutable.postValue(ViewState.Success)
        transactionMutable.value = users
    }

    private fun handleFailure(message: String?) {
        viewStateMutable.value = ViewState.Error(message)
    }

}