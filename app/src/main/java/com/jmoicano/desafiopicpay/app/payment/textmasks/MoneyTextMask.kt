package com.jmoicano.desafiopicpay.app.payment.textmasks

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*

class MoneyTextMask(private val editText: EditText) : TextWatcher {
    private var current = ""
    override fun beforeTextChanged(
        s: CharSequence,
        start: Int,
        count: Int,
        after: Int
    ) {
    }

    override fun onTextChanged(
        s: CharSequence,
        start: Int,
        before: Int,
        count: Int
    ) {
        if (s.toString() != current) {
            editText.removeTextChangedListener(this)
            val cleanString = s.toString().replace("[.,]".toRegex(), "")
            val parsed = cleanString.toDouble()
            val formatted = if (parsed > 0.0) {
                String.format(Locale("pt", "BR"), "%.2f", parsed / 100)
            } else {
                ""
            }
            current = formatted
            editText.setText(formatted)
            editText.setSelection(formatted.length)
            editText.addTextChangedListener(this)
        }
    }

    override fun afterTextChanged(s: Editable) {}

}