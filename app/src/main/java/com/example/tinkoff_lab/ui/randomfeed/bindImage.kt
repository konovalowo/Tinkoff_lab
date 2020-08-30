package com.example.tinkoff_lab.ui.randomfeed

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.tinkoff_lab.R

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {

    imgUrl?.let {
        val imgUri = Uri.parse(imgUrl.replace("http", "https"))

        val circularProgressDrawable = CircularProgressDrawable(imgView.context)
        circularProgressDrawable.apply {
            strokeWidth = 10f
            centerRadius = 40f
            start()
        }

        Glide.with(imgView.context)
            .load(imgUri)
            .transition(DrawableTransitionOptions.withCrossFade())
            .apply(RequestOptions()
                .placeholder(circularProgressDrawable)
                .error(R.drawable.ic_broken_image))
            .centerCrop()
            .into(imgView)
    }
}