package com.gilka.scubareddit.mvp.listing.contracts

import com.gilka.scubareddit.models.RedditEntry

interface Presenter {

    fun onDestroy()

    fun getEntries(channel: String, afterTag: String)

    fun filterRequested(filter: String)

    fun entryClicked(entry: RedditEntry)

    fun scrollToTopClicked()

}