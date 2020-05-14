package com.jmoicano.desafiopicpay.views.extensions

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.jmoicano.desafiopicpay.R
import com.jmoicano.desafiopicpay.api.creditcard.models.CreditCard

@BindingAdapter("android:text")
fun TextView.setCreditCard(creditCard: CreditCard?){
    text = context.getString(R.string.payment_card, creditCard?.number?.subSequence(0..3))
}