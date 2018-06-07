package com.gilka.scubareddit

import android.text.TextUtils
import android.widget.ImageView
import com.squareup.picasso.Picasso

fun ImageView.loadImg(imageUrl: String) {
    if (!TextUtils.isEmpty(imageUrl)) {
        Picasso.get()
                .load(imageUrl)
                .into(this)
    }
}