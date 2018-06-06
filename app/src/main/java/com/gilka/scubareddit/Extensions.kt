package com.gilka.scubareddit

import android.os.Parcel
import android.os.Parcelable
import android.text.TextUtils
import android.widget.ImageView
import com.squareup.picasso.Picasso

inline fun <reified T : Parcelable> createParcel(crossinline createFromParcel: (Parcel) -> T?): Parcelable.Creator<T> =
        object : Parcelable.Creator<T> {
            override fun createFromParcel(source: Parcel): T? = createFromParcel(source)
            override fun newArray(size: Int): Array<out T?> = arrayOfNulls(size)
        }

fun ImageView.loadImg(imageUrl: String) {
    if (!TextUtils.isEmpty(imageUrl)) {
        Picasso.get()
                .load(imageUrl)
                .into(this)
    }
}