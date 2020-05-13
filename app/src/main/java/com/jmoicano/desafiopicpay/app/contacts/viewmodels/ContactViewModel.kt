package com.jmoicano.desafiopicpay.app.contacts.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmoicano.desafiopicpay.api.model.Resource
import com.jmoicano.desafiopicpay.api.user.models.User
import com.jmoicano.desafiopicpay.app.contacts.repositories.ContactRepository
import com.jmoicano.desafiopicpay.handlers.ViewState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ContactViewModel(private val contactRepository: ContactRepository) : ViewModel() {

    private val viewStateMutable = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState>
        get() = viewStateMutable

    private val usersMutable = MutableLiveData<List<User>>()
    val users: LiveData<List<User>>
        get() = usersMutable

    private var job: Job? = null

    fun getContacts() {
        if (job != null) {
            job?.cancel()
        }
        job = viewModelScope.launch {
            viewStateMutable.postValue(ViewState.Loading)
            val result = contactRepository.getUsers()
            handleResult(result)
        }
    }

    private fun handleResult(resource: Resource<List<User>?>){
        when(resource){
            is Resource.Success -> {handleSuccess(resource.data)}
            is Resource.Failure -> {handleFailure(resource.error)}
        }
    }

    private fun handleSuccess(users: List<User>?){
        viewStateMutable.postValue(ViewState.Success)
        usersMutable.value = users
    }

    private fun handleFailure(message: String?){
        viewStateMutable.value = ViewState.Error(message)
    }

}