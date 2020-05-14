package com.jmoicano.desafiopicpay.app.creditcard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import com.jmoicano.desafiopicpay.R
import com.jmoicano.desafiopicpay.api.user.models.User
import com.jmoicano.desafiopicpay.databinding.ActivityEditCreditCardBinding
import com.jmoicano.desafiopicpay.views.extensions.dateFormat
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.ParseException

class EditCreditCardActivity : AppCompatActivity() {

    companion object{
        val CONTACT_EXTRA = "${EditCreditCardActivity::class.java.simpleName}.contact"

        fun Context.startEditCreditCard(user: User){
            val intent = Intent(this, EditCreditCardActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelable(CONTACT_EXTRA, user)
            intent.putExtras(bundle)
            this.startActivity(intent)
        }
    }

    private val contact: User? by lazy {
        intent.getParcelableExtra<User>(CONTACT_EXTRA)
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
    }

    private fun setupTextWatchers() {
        binding?.editCreditCardDueDateField?.doOnTextChanged { text, _, _, _ ->
            try {
                if (text?.length == 5)
                    viewModel.date.value = dateFormat.parse(text.toString())
            } catch (e: ParseException){}
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.editCreditToolbar)
        supportActionBar?.title = ""
        binding.editCreditToolbar.setNavigationOnClickListener { onBackPressed() }
    }
}
