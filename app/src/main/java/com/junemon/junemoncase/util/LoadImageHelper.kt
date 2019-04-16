package com.junemon.junemoncase.util

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.request.RequestOptions
import com.junemon.junemoncase.R


/**
 *
Created by Ian Damping on 15/04/2019.
Github = https://github.com/iandamping
 */
fun ImageView.loadUrlFullScreen(url: String?) {
    url?.let { Glide.with(context).load(it).into(this) }
}

fun ImageView.loadUrl(url: String?) {
    url?.let { Glide.with(context).load(it).apply(RequestOptions().override(600, 600).placeholder(R.drawable.empty_image)).into(this) }
}

private fun ImageView.imageThumbnail(urls: String?): RequestBuilder<Drawable> {
    return Glide.with(context).load(urls)
}

