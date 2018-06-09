package com.gilka.scubareddit.mvp.listing.contracts

import com.gilka.scubareddit.models.RedditEntry

interface Presenter {

    fun onDestroy()

    fun getMoreEntries()

    fun filterRequested(filter: String)

    fun refreshRequested()

    fun entryClicked(entry: RedditEntry)

    fun scrollToTopClicked()

}