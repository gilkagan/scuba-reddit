package com.gilka.scubareddit.models

import android.os.Parcel
import android.os.Parcelable
import com.gilka.scubareddit.createParcel

data class RedditEntry(
        val title: String,
        val thumbnail: String,
        val url: String
) : Parcelable, AdapterViewBase {

    companion object {
        @JvmField @Suppress("unused")
        val CREATOR = createParcel { RedditEntry(it) }
    }

    protected constructor(parcelIn: Parcel) : this(
            parcelIn.readString(),
            parcelIn.readString(),
            parcelIn.readString()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(title)
        dest.writeString(thumbnail)
        dest.writeString(url)
    }

    override fun describeContents() = 0
}