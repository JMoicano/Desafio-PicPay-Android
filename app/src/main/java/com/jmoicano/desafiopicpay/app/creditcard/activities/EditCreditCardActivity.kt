package com.jmoicano.desafiopicpay.app.creditcard.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import com.jmoicano.desafiopicpay.R
import com.jmoicano.desafiopicpay.api.creditcard.models.CreditCard
import com.jmoicano.desafiopicpay.api.user.models.User
import com.jmoicano.desafiopicpay.app.creditcard.viewmodels.EditCreditCardViewModel
import com.jmoicano.desafiopicpay.app.creditcard.textmasks.CreditCardTextMask
import com.jmoicano.desafiopicpay.app.creditcard.textmasks.DateTextMask
import com.jmoicano.desafiopicpay.app.payment.PaymentActivity.Companion.startPayment
import com.jmoicano.desafiopicpay.databinding.ActivityEditCreditCardBinding
import com.jmoicano.desafiopicpay.views.extensions.dateFormat
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.ParseException

class EditCreditCardActivity : AppCompatActivity() {

    companion object {
        val CONTACT_EXTRA = "${EditCreditCardActivity::class.java.simpleName}.contact"
        val CREDIT_CARD_EXTRA = "${EditCreditCardActivity::class.java.simpleName}.creditCard"

        fun Context.startEditCreditCard(user: User, creditCard: CreditCard? = null) {
            val intent = Intent(this, EditCreditCardActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelable(CONTACT_EXTRA, user)
            creditCard?.let { bundle.putParcelable(CREDIT_CARD_EXTRA, creditCard) }
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

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityEditCreditCardBinding>(
            this,
            R.layout.activity_edit_credit_card
        )
    }

    private val viewModel: EditCreditCardViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setupToolbar()
        setupTextWatchers()
        setCreditCard()
    }

    override fun onStart() {
        super.onStart()
        binding.editCreditCardButton.setOnClickListener {
            contact?.let { contact ->
                    startPayment(contact, viewModel.getCreditCard())
            }
        }
        binding.editCreditCardCvvField.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE &&
                    viewModel.buttonVisible.value == true) {
                binding.editCreditCardButton.callOnClick()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    private fun setCreditCard() {
        creditCard?.let {
            viewModel.setCreditCard(it)
            binding?.editCreditCardDueDateField?.setText(dateFormat.format(it.dueDate))
        }
    }

    private fun setupTextWatchers() {
        binding?.editCreditCardDueDateField?.doOnTextChanged { text, _, _, _ ->
            try {
                if (text?.length == 5)
                    viewModel.date.value = dateFormat.parse(text.toString())
            } catch (e: ParseException) {
            }
        }
        binding?.editCreditCardDueDateField?.addTextChangedListener(
            DateTextMask(
                binding?.editCreditCardDueDateField
            )
        )
        binding?.editCreditCardNumberField?.addTextChangedListener(CreditCardTextMask())
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.editCreditToolbar)
        supportActionBar?.title = ""
        binding.editCreditToolbar.setNavigationOnClickListener { onBackPressed() }
    }
}
