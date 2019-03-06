package com.mbojec.halo.utils

import android.view.View
import android.widget.ImageView
import android.widget.Toolbar
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.mbojec.halo.R

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

@BindingAdapter("android:title")
fun setTitle(toolbar: androidx.appcompat.widget.Toolbar, title: String?){
    title?.let { toolbar.title = title }
}

@BindingAdapter("app:navigationIcon")
fun setIcon(toolbar: androidx.appcompat.widget.Toolbar, cityId: Long?){
    cityId?.let {
        if (cityId <= 1){
            toolbar.setNavigationIcon(R.drawable.ic_location)
        } else {
            toolbar.navigationIcon = null
        }
    }
}
