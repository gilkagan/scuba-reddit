package com.gilka.scubareddit.models

import android.os.Parcel
import android.os.Parcelable

data class RedditListing(
        val after: String,
        val before: String,
        val entries: List<RedditEntry>) : Parcelable {

    companion object CREATOR : Parcelable.Creator<RedditListing> {
        override fun createFromParcel(parcel: Parcel): RedditListing {
            return RedditListing(parcel)
        }

        override fun newArray(size: Int): Array<RedditListing?> {
            return arrayOfNulls(size)
        }
    }

    protected constructor(parcelIn: Parcel) : this(
            parcelIn.readString(),
            parcelIn.readString(),
            mutableListOf<RedditEntry>().apply {
                parcelIn.readTypedList(this, RedditEntry.CREATOR)
            }
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(after)
        dest.writeString(before)
        dest.writeTypedList(entries)
    }

    override fun describeContents() = 0
}