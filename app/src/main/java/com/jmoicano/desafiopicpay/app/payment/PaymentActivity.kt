package com.jmoicano.desafiopicpay.app.payment

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.jmoicano.desafiopicpay.R
import com.jmoicano.desafiopicpay.api.creditcard.models.CreditCard
import com.jmoicano.desafiopicpay.api.user.models.User
import com.jmoicano.desafiopicpay.databinding.ActivityPaymentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class PaymentActivity : AppCompatActivity() {

    companion object{
        val CONTACT_EXTRA = "${PaymentActivity::class.java.simpleName}.contact"
        val CREDIT_CARD_EXTRA = "${PaymentActivity::class.java.simpleName}.creditCard"

        fun Context.startPayment(user: User, creditCard: CreditCard){
            val intent = Intent(this, PaymentActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelable(CONTACT_EXTRA, user)
            bundle.putParcelable(CREDIT_CARD_EXTRA, creditCard)
            intent.putExtras(bundle)
            this.startActivity(intent)
        }
    }

    private val contact: User? by lazy {
        intent.getParcelableExtra<User>(CONTACT_EXTRA)
    }

    private val creditCard: CreditCard? by lazy {
        intent.getParcelableExtra<CreditCard>(CREDIT_CARD_EXTRA)
    }

    private val viewModel: PaymentViewModel by viewModel()

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityPaymentBinding>(
            this,
            R.layout.activity_payment
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        creditCard?.let { viewModel.setCreditCard(it) }
        contact?.let { viewModel.setDestinationUser(it) }
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setupToolbar()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.paymentToolbar)
        supportActionBar?.title = ""
        binding.paymentToolbar.setNavigationOnClickListener { onBackPressed() }
    }
}
