package com.gilka.scubareddit.listing


import android.app.Activity
import android.app.ActivityOptions
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import android.support.v7.widget.SearchView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gilka.scubareddit.R
import com.gilka.scubareddit.base.BaseFragment
import com.gilka.scubareddit.custom.LoadMoreScrollListener
import com.gilka.scubareddit.custom.LoadMoreScrollListener.OnLoadMoreNeededListener
import com.gilka.scubareddit.models.AdapterItemBase
import com.gilka.scubareddit.models.RedditEntry
import com.gilka.scubareddit.models.RedditListing
import com.gilka.scubareddit.viewentry.ViewEntryActivity
import kotlinx.android.synthetic.main.fragment_listing.*


abstract class BaseListingFragment(private val usePaging: Boolean = false, private val useFilter: Boolean = false) : BaseFragment(),
                        ListingAdapter.OnItemClickListener,
                        OnLoadMoreNeededListener {

    companion object {
        private const val TAG_LISTING = "redditListing"
    }

    open var redditListing: RedditListing? = null
    val adapter: ListingAdapter by lazy {
        ListingAdapter(activity as Activity, this, usePaging)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_listing, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // initAdapter
        if (rvListing.adapter == null) {
            rvListing.adapter = adapter

            rvListing.setHasFixedSize(true)
            val linearLayout = LinearLayoutManager(context)
            rvListing.layoutManager = linearLayout

            if (usePaging) {
                rvListing.addOnScrollListener(LoadMoreScrollListener(this, linearLayout))
            }

            val snapHelper = LinearSnapHelper()
            snapHelper.attachToRecyclerView(rvListing)
        }

        filter.visibility = when (useFilter) {
            true -> View.VISIBLE
            else -> View.GONE
        }

        filter.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                adapter.applyFilter(query)
                return false
            }
        })

        swipe_refresher.isEnabled = false

        fabScrollToTop.setOnClickListener({
            rvListing.scrollToPosition(0)
        })

        // check if first time
        if (savedInstanceState != null && savedInstanceState.containsKey(TAG_LISTING)) {
            redditListing = savedInstanceState.get(TAG_LISTING) as RedditListing
            (rvListing.adapter as ListingAdapter).loadEntries(redditListing!!.entries)
        } else {
            getMoreEntries()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val entries = adapter.getEntries()
        if (redditListing != null && entries.isNotEmpty()) {
            outState.putParcelable(TAG_LISTING, redditListing?.copy(entries = entries))
        }
    }

    abstract fun getMoreEntries()

    override fun onLoadMoreNeeded() {
        getMoreEntries()
    }

    override fun onItemClick(item: AdapterItemBase) {
        if (item is RedditEntry) {
            val intent = ViewEntryActivity.newIntent(context!!, item)
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle())
        }
    }

}
