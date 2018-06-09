package com.gilka.scubareddit.mvp.listing.presenter

import com.gilka.scubareddit.models.RedditEntry
import com.gilka.scubareddit.mvp.listing.contracts.Model
import com.gilka.scubareddit.mvp.listing.contracts.Presenter
import com.gilka.scubareddit.mvp.listing.contracts.View

class ListingPresenter(var view: View?, channel: String) : Presenter,
        Model.OnEntriesReadyListener {

    private val model by lazy { com.gilka.scubareddit.mvp.listing.model.ListingModel(channel) }

    override fun getMoreEntries() {
        view?.showProgress()
        model.getRedditEntries(this)
    }

    override fun refreshRequested() {
        view?.showProgress()
        model.refresh(this)
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

    override fun onEntriesReady(entries: ArrayList<RedditEntry>) {
        view?.onDataReady(entries)
        view?.hideProgress()
    }

    override fun onFailure(t: Throwable) {
        view?.onDataFail(t)
    }
}