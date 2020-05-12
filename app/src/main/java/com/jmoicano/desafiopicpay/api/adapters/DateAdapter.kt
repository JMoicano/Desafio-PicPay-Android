package com.jmoicano.desafiopicpay.api.adapters

import android.util.Log
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateAdapter {
    private val df = SimpleDateFormat("MM/yy", Locale.getDefault())

    @ToJson
    fun toJson(date: Date): String {
        return try {
            df.format(date)
        } catch (exception: ParseException) {
            Log.e("DateAdapter", exception.message)
            ""
        }
    }

}
