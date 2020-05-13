package com.jmoicano.desafiopicpay.app.contacts.activities

import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.jmoicano.desafiopicpay.R
import com.jmoicano.desafiopicpay.app.contacts.adapters.UserAdapter
import com.jmoicano.desafiopicpay.app.contacts.viewmodels.ContactViewModel
import com.jmoicano.desafiopicpay.app.creditcard.CreditCardPrimingActivity.Companion.startCreditCardPriming
import com.jmoicano.desafiopicpay.databinding.ActivityContactsBinding
import com.jmoicano.desafiopicpay.handlers.ViewState
import kotlinx.android.synthetic.main.activity_contacts.*
import kotlinx.android.synthetic.main.content_contacts.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ContactsActivity : AppCompatActivity() {

    private val adapter by lazy {
        UserAdapter { selectedUser ->
            startCreditCardPriming(selectedUser)
        }
    }

    private val viewModel: ContactViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityContactsBinding>(this, R.layout.activity_contacts)
        setSupportActionBar(binding.toolbar)
        binding.viewModel = viewModel
        setUsersData()
        viewModel.getContacts()
    }

    override fun onStart() {
        super.onStart()
        setObservers()
    }

    override fun onStop() {
        super.onStop()
        unregisterObservers()
    }

    private fun setObservers() {
        viewModel.viewState.observe(this, Observer {state ->
            if (state is ViewState.Success){
                viewModel.users.value?.let { adapter.items = it }
            } else if (state is ViewState.Error){
                Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun unregisterObservers() {
        viewModel.viewState.removeObservers(this)
    }

    private fun setUsersData() {
        contactsUserList.adapter = adapter
        contactsSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.filter.filter(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }

        })
    }
}
