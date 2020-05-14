package com.jmoicano.desafiopicpay.app.creditcard.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jmoicano.desafiopicpay.R
import com.jmoicano.desafiopicpay.api.user.models.User
import com.jmoicano.desafiopicpay.app.creditcard.activities.EditCreditCardActivity.Companion.startEditCreditCard
import kotlinx.android.synthetic.main.activity_credit_card_priming.*

class CreditCardPrimingActivity : AppCompatActivity() {

    companion object{
        val CONTACT_EXTRA = "${CreditCardPrimingActivity::class.java.simpleName}.contact"

        fun Context.startCreditCardPriming(user: User){
            val intent = Intent(this, CreditCardPrimingActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelable(CONTACT_EXTRA, user)
            intent.putExtras(bundle)
            this.startActivity(intent)
        }
    }

    private val contact: User? by lazy {
        intent.getParcelableExtra<User>(CONTACT_EXTRA)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credit_card_priming)
        setupToolbar()
    }

    private fun setupToolbar() {
        setSupportActionBar(creditCardPrimingToolbar)
        supportActionBar?.title = ""
        creditCardPrimingToolbar.setNavigationOnClickListener { onBackPressed() }
    }

    override fun onStart() {
        super.onStart()
        creditCardPrimingCardButton.setOnClickListener {
            contact?.let { user -> startEditCreditCard(user) }
        }
    }

    override fun onStop() {
        super.onStop()
        creditCardPrimingCardButton.setOnClickListener(null)
    }
}
