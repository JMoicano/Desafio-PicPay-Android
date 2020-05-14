package com.jmoicano.desafiopicpay.views.extensions

import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import com.jmoicano.desafiopicpay.handlers.ViewState
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

val dateFormat = SimpleDateFormat("MM/yy", Locale.getDefault())

@BindingAdapter("android:visibility")
fun View.viewStateLoading(viewState: ViewState?) {
    this.isVisible = viewState is ViewState.Loading
}

@BindingAdapter("android:visibility")
fun View.boolVisibility(visible: Boolean?) {
    this.isVisible = visible == true
}