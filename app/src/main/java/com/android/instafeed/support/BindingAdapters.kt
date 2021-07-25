package com.android.instafeed.support

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.android.instafeed.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions


val requestOptions = RequestOptions()
    .centerCrop()
    .placeholder(R.drawable.ic_launcher_foreground)
    .diskCacheStrategy(DiskCacheStrategy.ALL)
    .fallback(ColorDrawable(Color.GRAY))


@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String) {
    Glide.with(view.context)
        .load(url)
        .apply(requestOptions)
        .into(view)
}
