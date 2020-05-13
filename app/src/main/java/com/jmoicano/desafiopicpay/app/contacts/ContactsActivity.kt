package com.jmoicano.desafiopicpay.app.contacts

import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.jmoicano.desafiopicpay.R
import com.jmoicano.desafiopicpay.api.user.models.User
import kotlinx.android.synthetic.main.activity_contacts.*
import kotlinx.android.synthetic.main.content_contacts.*

class ContactsActivity : AppCompatActivity() {

    private val adapter by lazy {
        UserAdapter { selectedUser ->
            TODO("not implemented function")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)
        setSupportActionBar(toolbar)
        setUsersData()
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
