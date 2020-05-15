package com.jmoicano.desafiopicpay.views.extensions

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.jmoicano.desafiopicpay.R
import com.jmoicano.desafiopicpay.api.creditcard.models.CreditCard
import java.math.BigDecimal
import java.text.DateFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("android:text")
fun TextView.setCreditCard(creditCard: CreditCard?){
    text = context.getString(R.string.payment_card, creditCard?.number?.subSequence(0..3))
}

@BindingAdapter("money")
fun TextView.money(value: BigDecimal?) {
    text = value?.let {
        NumberFormat.getCurrencyInstance(
            Locale("pt", "BR")
        ).format(it.toDouble())
    }
}

@BindingAdapter("timestamp")
fun TextView.timestamp(timestamp: Long?){
    val dateFormat = SimpleDateFormat("dd/MM/yyyy 'às' HH:mm", Locale("pt", "BR"))
    text = timestamp?.let { dateFormat.format(Date(it)) }
}