package com.jmoicano.desafiopicpay.views.extensions

import android.view.View
import androidx.databinding.BindingAdapter
import com.jmoicano.desafiopicpay.handlers.ViewState

@BindingAdapter("view_state_loading")
fun View.viewStateLoadgin(viewState: ViewState?) {
    visibility = if (viewState is ViewState.Loading)
        View.VISIBLE
    else
        View.GONE
}