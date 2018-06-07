package com.gilka.scubareddit.models

import android.os.Parcel
import android.os.Parcelable

data class RedditEntry(
        val title: String,
        val thumbnail: String,
        val url: String
) : Parcelable, AdapterViewBase {

    companion object CREATOR : Parcelable.Creator<RedditEntry> {
        override fun createFromParcel(parcel: Parcel): RedditEntry {
            return RedditEntry(parcel)
        }

        override fun newArray(size: Int): Array<RedditEntry?> {
            return arrayOfNulls(size)
        }
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