package com.gilka.scubareddit.mvp.listing.contracts

import com.gilka.scubareddit.models.RedditEntry

interface Model {

    interface OnGetEntriesFinishedListener {
        fun onFinished(entries: List<RedditEntry>, afterTag: String)
        fun onFailure(t: Throwable)
    }

    interface OnFilterFinishedListener {
        fun onFinished(entries: List<RedditEntry>)
    }

    fun getRedditEntries(onFinishedListener: OnGetEntriesFinishedListener, channel: String, afterTag: String)

    fun applyFilter(onFilterFinishedListener: OnFilterFinishedListener, filter: String)

}