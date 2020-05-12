package com.jmoicano.desafiopicpay.app.contacts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jmoicano.desafiopicpay.R
import kotlinx.android.synthetic.main.activity_contacts.*

class ContactsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)
        setSupportActionBar(toolbar)
    }
}
