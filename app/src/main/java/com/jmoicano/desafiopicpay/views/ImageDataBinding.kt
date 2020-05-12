package com.jmoicano.desafiopicpay.views

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("circular_image_by_url")
fun ImageView.circularImageByUrl(url: String) {
    Glide.with(this)
        .load(url)
        .apply(RequestOptions.circleCropTransform())
        .into(this)
}