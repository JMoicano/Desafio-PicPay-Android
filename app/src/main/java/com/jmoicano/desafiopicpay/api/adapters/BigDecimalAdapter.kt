package com.jmoicano.desafiopicpay.api.adapters

import android.util.Log
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.lang.NumberFormatException
import java.math.BigDecimal
import java.text.ParseException

class BigDecimalAdapter {

    @ToJson
    fun toJson(number: BigDecimal): String {
        return try {
            number.toPlainString()
        } catch (exception: ParseException) {
            exception.message?.let { Log.e("BigDecimalAdapter", it) }
            ""
        }
    }

    @FromJson
    fun fromJson(json: String): BigDecimal {
        return try {
            BigDecimal(json)
        }catch (exception: NumberFormatException){
            exception.message?.let { Log.e("BigDecimalAdapter", it) }
            BigDecimal(0)
        }
    }
}