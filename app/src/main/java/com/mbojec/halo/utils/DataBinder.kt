package com.mbojec.halo.utils

import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mbojec.halo.Const
import com.mbojec.halo.model.RecyclerViewConfiguration
import com.mbojec.halo.ui.ListFragmentDirections

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
            toolbar.setNavigationIcon(com.mbojec.halo.R.drawable.ic_location)
        } else {
            toolbar.navigationIcon = null
        }
    }
}

@BindingAdapter("app:configuration")
fun bindRecyclerViewConfiguration(view: RecyclerView, config: RecyclerViewConfiguration?) {
    if (config != null) {
        view.layoutManager = config.getLayoutManager()
        view.adapter = config.getAdapter()

    }
}

@BindingAdapter("app:click")
fun setClickListener(cardView: CardView, cityId: Long){
    cardView.setOnClickListener {
        val action = ListFragmentDirections.actionListDestToMainDest(cityId)
        val handler = Handler()
        val task = Runnable {
            it.findNavController().navigate(action)
        }
        handler.postDelayed(task, 500)
    }
}

@BindingAdapter("app:fabClick")
fun setFABClickListener(button: FloatingActionButton, boolean: Boolean){
    if (boolean){
        button.setOnClickListener {
            val action = ListFragmentDirections.actionListDestToSearchDest()
            it.findNavController().navigate(action)
        }
    }
}

@BindingAdapter("app:onClick")
fun setLogoClickListener(view: TextView, boolean: Boolean){
    if (boolean){
        view.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(Const.DARK_SKY_SITE_URL))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            it.context.applicationContext.startActivity(intent)
        }
    }
}