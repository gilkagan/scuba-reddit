package com.gilka.scubareddit.mvp.listing.contracts

import com.gilka.scubareddit.models.RedditEntry

interface Model {

    interface OnFinishedListener {
        fun onFinished(entries: List<RedditEntry>, afterTag: String)
        fun onFailure(t: Throwable)
    }

    fun getRedditEntries(onFinishedListener: OnFinishedListener, channel: String, afterTag: String)
}