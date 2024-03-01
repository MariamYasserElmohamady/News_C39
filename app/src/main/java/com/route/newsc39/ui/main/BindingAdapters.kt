package com.route.newsc39.ui.main

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun loadImageIntoImageView(imageView: ImageView, url: String){
    Glide.with(imageView)
        .load(url)
        .into(imageView)
}