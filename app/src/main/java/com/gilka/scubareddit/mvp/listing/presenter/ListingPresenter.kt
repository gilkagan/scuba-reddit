package com.gilka.scubareddit.mvp.listing.presenter

import com.gilka.scubareddit.models.RedditEntry
import com.gilka.scubareddit.mvp.listing.contracts.Model
import com.gilka.scubareddit.mvp.listing.contracts.Presenter
import com.gilka.scubareddit.mvp.listing.contracts.View

class ListingPresenter(var view: View?) : Presenter,
        Model.OnGetEntriesFinishedListener,
        Model.OnFilterFinishedListener {

    private val model by lazy { com.gilka.scubareddit.mvp.listing.model.ListingModel() }

    override fun getEntries(channel: String, afterTag: String) {
        view?.showProgress()
        model.getRedditEntries(this, channel, afterTag)
    }

    override fun filterRequested(filter: String) {
        model.applyFilter(this, filter)
    }

    override fun entryClicked(entry: RedditEntry) {
        view?.clickOnEntry(entry)
    }

    override fun scrollToTopClicked() {
        view?.scrollToTop()
    }

    override fun onDestroy() {
        view = null
    }

    override fun onFinished(entries: List<RedditEntry>, afterTag: String) {
        view?.onLoadSuccess(entries, afterTag)
        view?.hideProgress()
    }

    override fun onFinished(entries: List<RedditEntry>) {
        view?.onFilter(entries)
    }

    override fun onFailure(t: Throwable) {
        view?.onLoadFail(t)
    }
}