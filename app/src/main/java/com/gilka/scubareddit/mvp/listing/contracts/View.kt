package com.gilka.scubareddit.mvp.listing.contracts

import com.gilka.scubareddit.models.RedditEntry

interface View {

    fun showProgress()

    fun hideProgress()

    fun clickOnEntry(entry: RedditEntry)

    fun scrollToTop()

    fun onLoadSuccess(entries: List<RedditEntry>, afterTag: String)

    fun onLoadFail(throwable: Throwable)
}