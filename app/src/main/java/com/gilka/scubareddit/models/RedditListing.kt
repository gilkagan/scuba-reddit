package com.gilka.scubareddit.models

import android.os.Parcel
import android.os.Parcelable
import com.gilka.scubareddit.createParcel

data class RedditListing(
        val after: String,
        val before: String,
        val entries: List<RedditEntry>) : Parcelable {

    companion object {
        @JvmField @Suppress("unused")
        val CREATOR = createParcel { RedditListing(it) }
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