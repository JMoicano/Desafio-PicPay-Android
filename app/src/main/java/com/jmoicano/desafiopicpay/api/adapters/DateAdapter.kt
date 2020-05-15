package com.jmoicano.desafiopicpay.api.adapters

import android.util.Log
import com.jmoicano.desafiopicpay.views.extensions.dateFormat
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateAdapter {

    @ToJson
    fun toJson(date: Date): String {
        return try {
            dateFormat.format(date)
        } catch (exception: ParseException) {
            exception.message?.let { Log.e("DateAdapter", it) }
            ""
        }
    }

    @FromJson
    fun fromJson(json: String): Date? {
        return try {
            dateFormat.parse(json)
        } catch (exception: ParseException) {
            exception.message?.let { Log.e("DateAdapter", it) }
            null
        }
    }
}
