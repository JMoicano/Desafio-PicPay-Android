package com.jmoicano.desafiopicpay.app.payment.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.jmoicano.desafiopicpay.R
import com.jmoicano.desafiopicpay.api.creditcard.models.CreditCard
import com.jmoicano.desafiopicpay.api.user.models.User
import com.jmoicano.desafiopicpay.app.creditcard.activities.EditCreditCardActivity.Companion.startEditCreditCard
import com.jmoicano.desafiopicpay.app.payment.fragments.TransactionFragment
import com.jmoicano.desafiopicpay.app.payment.fragments.TransactionFragment.Companion.startTransactionFragment
import com.jmoicano.desafiopicpay.app.payment.viewmodels.PaymentViewModel
import com.jmoicano.desafiopicpay.app.payment.textmasks.MoneyTextMask
import com.jmoicano.desafiopicpay.databinding.ActivityPaymentBinding
import com.jmoicano.desafiopicpay.handlers.ViewState
import org.koin.androidx.viewmodel.ext.android.viewModel

class PaymentActivity : AppCompatActivity() {

    companion object {
        val CONTACT_EXTRA = "${PaymentActivity::class.java.simpleName}.contact"
        val CREDIT_CARD_EXTRA = "${PaymentActivity::class.java.simpleName}.creditCard"

        fun Context.startPayment(user: User, creditCard: CreditCard) {
            val intent = Intent(this, PaymentActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelable(CONTACT_EXTRA, user)
            bundle.putParcelable(CREDIT_CARD_EXTRA, creditCard)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
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

    override fun onStart() {
        super.onStart()
        registerClickListeners()
        setTextWatchers()
        setObersvers()
    }

    private fun setObersvers() {
        viewModel.viewState.observe(this, Observer { state ->
            if (state is ViewState.Success) {
                startBottomSheet()
            } else if (state is ViewState.Error) {
                Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun startBottomSheet() {
        creditCard?.let { creditCard ->
            viewModel.transaction.value?.let { transaction ->
                startTransactionFragment(creditCard, transaction)
                    .show(
                        supportFragmentManager,
                        TransactionFragment.TAG
                    )
            }
        }
    }

    private fun setTextWatchers() {
        binding.paymentValue.addTextChangedListener(MoneyTextMask(binding.paymentValue))
    }

    private fun registerClickListeners() {
        binding.paymentEditLink.setOnClickListener { _ ->
            contact?.let {
                startEditCreditCard(it, creditCard)
            }
        }
        binding.paymentButton.setOnClickListener {
            viewModel.pay()
        }
        binding.paymentValue.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE && viewModel.valueFilled.value == true) {
                binding.paymentButton.callOnClick()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    override fun onStop() {
        super.onStop()
        unregisterClickListeners()
        unsetTextWatchers()
        unsetObervers()
    }

    private fun unsetObervers() {
        viewModel.viewState.removeObservers(this)
    }

    private fun unsetTextWatchers() {
        binding.paymentValue.addTextChangedListener(null)
    }

    private fun unregisterClickListeners() {
        binding.paymentEditLink.setOnClickListener(null)
        binding.paymentValue.setOnEditorActionListener(null)
        binding.paymentButton.setOnClickListener(null)
    }
}
