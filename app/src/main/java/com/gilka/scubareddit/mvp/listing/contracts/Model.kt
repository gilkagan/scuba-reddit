package com.gilka.scubareddit.mvp.listing.contracts

import com.gilka.scubareddit.models.RedditEntry

interface Model {

    interface OnEntriesReadyListener {
        fun onEntriesReady(entries: ArrayList<RedditEntry>)
        fun onFailure(t: Throwable)
    }

    fun getRedditEntries(onEntriesReadyListener: OnEntriesReadyListener)

    fun refresh(onEntriesReadyListener: OnEntriesReadyListener)

    fun applyFilter(onEntriesReadyListener: OnEntriesReadyListener, filter: String)

}