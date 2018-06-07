package com.gilka.scubareddit.listing


import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gilka.scubareddit.R
import com.gilka.scubareddit.base.BaseFragment
import com.gilka.scubareddit.custom.LoadMoreScrollListener
import com.gilka.scubareddit.custom.LoadMoreScrollListener.onLoadMoreNeededListener
import com.gilka.scubareddit.models.AdapterViewBase
import com.gilka.scubareddit.models.RedditEntry
import com.gilka.scubareddit.models.RedditListing
import com.gilka.scubareddit.viewentry.ViewEntryActivity
import kotlinx.android.synthetic.main.fragment_listing.*


abstract class BaseListingFragment : BaseFragment(),
                        ListingAdapter.OnItemClickListener,
                        onLoadMoreNeededListener {

    companion object {
        private const val TAG_LISTING = "redditListing"
    }

    open var redditListing: RedditListing? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_listing, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // initAdapter
        if (rvListing.adapter == null) {
            rvListing.adapter = ListingAdapter(activity as Activity, this, usePaging())
            rvListing.setHasFixedSize(true)
            val linearLayout = LinearLayoutManager(context)
            rvListing.layoutManager = linearLayout

            if (usePaging()) {
                rvListing.addOnScrollListener(LoadMoreScrollListener(this, linearLayout))
            }

            val snapHelper = LinearSnapHelper()
            snapHelper.attachToRecyclerView(rvListing)
        }

        fabScrollToTop.setOnClickListener({
            rvListing.scrollToPosition(0)
        })

        // check if first time
        if (savedInstanceState != null && savedInstanceState.containsKey(TAG_LISTING)) {
            redditListing = savedInstanceState.get(TAG_LISTING) as RedditListing
            (rvListing.adapter as ListingAdapter).setInitialEntries(redditListing!!.entries)
        } else {
            getMoreEntries()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val entries = (rvListing.adapter as ListingAdapter).getEntries()
        if (redditListing != null && entries.isNotEmpty()) {
            outState.putParcelable(TAG_LISTING, redditListing?.copy(entries = entries))
        }
    }

    abstract fun getMoreEntries()

    abstract fun usePaging() : Boolean

    override fun onLoadMoreNeeded() {
        getMoreEntries()
    }

    override fun onItemClick(item: AdapterViewBase) {
        if (item is RedditEntry) {
            val intent = ViewEntryActivity.newIntent(context!!, item)
            startActivity(intent)
        }
    }

}
