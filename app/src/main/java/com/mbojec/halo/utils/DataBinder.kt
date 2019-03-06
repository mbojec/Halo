package com.mbojec.halo.utils

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, resourceId: Int) {
    val context = imageView.context
    Glide.with(context).load(resourceId).into(imageView)
}

@BindingAdapter("android:visibility")
fun setVisibility(view: View, value: Boolean) {
    view.visibility = if (value) View.VISIBLE else View.INVISIBLE
}

@BindingAdapter("android:background")
fun setBackground(view: View, colorId: Int){
    view.setBackgroundColor(colorId)
}

@BindingAdapter("card_view:cardBackgroundColor")
fun setBackgroundColor(view: View, colorId: Int){
    view.setBackgroundColor(colorId)
}