package com.jmoicano.desafiopicpay.app.creditcard.textmasks

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.util.*

class DateTextMask(private val date: EditText?) : TextWatcher {
    private var current = ""
    private val ddmmyyyy = "MMYY"
    private val cal = Calendar.getInstance()
    override fun onTextChanged(
        s: CharSequence,
        start: Int,
        before: Int,
        count: Int
    ) {
        if (s.toString() != current) {
            var clean = s.toString().replace("[^\\d.]|\\.".toRegex(), "")
            val cleanC = current.replace("[^\\d.]|\\.".toRegex(), "")
            val cl = clean.length
            var sel = cl
            var i = 2
            while (i <= cl && i < 6) {
                sel++
                i += 2
            }
            //Fix for pressing delete next to a forward slash
            if (clean == cleanC) sel--
            if (clean.length < 8) {
                clean = clean + ddmmyyyy.substring(clean.length)
            } else {
                //This part makes sure that when we finish entering numbers
                //the date is correct, fixing it otherwise
                var mon = clean.substring(0, 2).toInt()
                var year = clean.substring(2, 4).toInt()
                mon = if (mon < 1) 1 else if (mon > 12) 12 else mon
                cal[Calendar.MONTH] = mon - 1
                year = if (year < 1900) 1900 else if (year > 2100) 2100 else year
                cal[Calendar.YEAR] = year
                // ^ first set year for the line below to work correctly
                //with leap years - otherwise, date e.g. 29/02/2012
                //would be automatically corrected to 28/02/2012
                clean = String.format("%02d%02d", mon, year)
            }
            clean = String.format(
                "%s/%s", clean.substring(0, 2),
                clean.substring(2, 4)
            )
            sel = if (sel < 0) 0 else sel
            current = clean
            date?.setText(current)
            date?.setSelection(if (sel < current.length) sel else current.length)
        }
    }

    override fun beforeTextChanged(
        s: CharSequence,
        start: Int,
        count: Int,
        after: Int
    ) {
    }

    override fun afterTextChanged(s: Editable) {}

}