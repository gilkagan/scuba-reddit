package com.gilka.scubareddit.mvp.listing.view

import android.app.ActivityOptions
import android.support.v4.app.Fragment
import com.gilka.scubareddit.models.RedditEntry
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import android.support.v7.widget.SearchView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.gilka.scubareddit.R
import com.gilka.scubareddit.custom.LoadMoreScrollListener
import com.gilka.scubareddit.mvp.listing.contracts.Presenter
import com.gilka.scubareddit.mvp.listing.presenter.ListingPresenter
import com.gilka.scubareddit.viewentry.ViewEntryActivity
import kotlinx.android.synthetic.main.fragment_listing.*
import java.util.*




class ListingFragment :
        Fragment(),
        com.gilka.scubareddit.mvp.listing.contracts.View,
        ListingSimpleAdapter.OnItemClickListener,
        LoadMoreScrollListener.OnLoadMoreNeededListener {

    private val presenter: Presenter
    private val adapter by lazy { ListingSimpleAdapter((activity as android.app.Activity), entries, this) }
    private val channel = "diving"
    private var entries: ArrayList<RedditEntry> = ArrayList()
    private var loadMoreScrollListener : LoadMoreScrollListener? = null
    private var isResultChanged : Boolean = false

    companion object {
        private const val TAG_LISTING = "redditListing"
    }

    init {
        presenter = ListingPresenter(this, channel)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_listing, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rvListing.setHasFixedSize(true)
        val linearLayout = LinearLayoutManager(context)
        rvListing.layoutManager = linearLayout
        rvListing.adapter = adapter
        loadMoreScrollListener = LoadMoreScrollListener(this, linearLayout)
        rvListing.addOnScrollListener(loadMoreScrollListener)

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(rvListing)

        fabScrollToTop.setOnClickListener({
            presenter.scrollToTopClicked()
        })

        filter.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                presenter.filterRequested(query)
                isResultChanged = true
                return false
            }
        })

        swipe_refresher.setOnRefreshListener({
            presenter.refreshRequested()
            isResultChanged = true
        })

        if (savedInstanceState != null && savedInstanceState.containsKey(ListingFragment.TAG_LISTING)) {
            val values = savedInstanceState.getParcelableArrayList<RedditEntry>(ListingFragment.TAG_LISTING)
            entries.clear()
            entries.addAll(values)
        } else {
            presenter.getMoreEntries()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (entries.isNotEmpty()) {
            outState.putParcelableArrayList(ListingFragment.TAG_LISTING, entries)
        }
    }

    override fun onItemClick(item: RedditEntry) {
        presenter.entryClicked(item)
    }

    override fun onLoadMoreNeeded() {
        presenter.getMoreEntries()
    }

    override fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress.visibility = View.GONE
    }

    override fun clickOnEntry(entry: RedditEntry) {
        val intent = ViewEntryActivity.newIntent(context!!, entry)
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle())
    }

    override fun scrollToTop() {
        rvListing.scrollToPosition(0)
    }

    override fun onDataReady(entries: ArrayList<RedditEntry>) {
        this.entries.clear()
        this.entries.addAll(entries)
        adapter.notifyDataSetChanged()
        if (isResultChanged) {
            // reset listener
            loadMoreScrollListener?.referesh()
            swipe_refresher.isRefreshing = false
            isResultChanged = false
        }

    }

    override fun onDataFail(throwable: Throwable) {
        Toast.makeText(activity, throwable.message, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

}