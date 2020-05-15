package com.jmoicano.desafiopicpay.app.payment.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jmoicano.desafiopicpay.R
import com.jmoicano.desafiopicpay.api.creditcard.models.CreditCard
import com.jmoicano.desafiopicpay.api.payment.models.Payment
import com.jmoicano.desafiopicpay.api.payment.models.Transaction
import com.jmoicano.desafiopicpay.databinding.BottomSheetDialogTransactionBinding

class TransactionFragment : BottomSheetDialogFragment() {

    companion object {

        val TAG = TransactionFragment::class.java.simpleName
        val CREDIT_CARD_EXTRA = "${Transaction::class.java.simpleName}.creditCard"
        val TRANSACTION_EXTRA = "${Transaction::class.java.simpleName}.transaction"

        fun Context.startTransactionFragment(creditCard: CreditCard, transaction: Transaction) : BottomSheetDialogFragment{
            val transactionFragment = TransactionFragment()
            val args = Bundle()
            args.putParcelable(CREDIT_CARD_EXTRA, creditCard)
            args.putParcelable(TRANSACTION_EXTRA, transaction)
            transactionFragment.arguments = args
            return transactionFragment
        }
    }

    private val creditCard: CreditCard? by lazy {
        arguments?.getParcelable<CreditCard>(CREDIT_CARD_EXTRA)
    }

    private val transaction:Transaction? by lazy {
        arguments?.getParcelable<Transaction>(TRANSACTION_EXTRA)
    }

    private lateinit var binding: BottomSheetDialogTransactionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.bottom_sheet_dialog_transaction,
                container,
                false
            )
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.creditCard = creditCard
        binding.transaction = transaction
    }
}