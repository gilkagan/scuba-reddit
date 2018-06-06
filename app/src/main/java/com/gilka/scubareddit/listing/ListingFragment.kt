package com.gilka.scubareddit.listing


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.gilka.scubareddit.R
import com.gilka.scubareddit.RedditDataManager
import com.gilka.scubareddit.base.BaseFragment
import com.gilka.scubareddit.custom.LoadMoreScrollListener
import com.gilka.scubareddit.models.AdapterViewBase
import com.gilka.scubareddit.models.RedditEntry
import com.gilka.scubareddit.models.RedditListing
import com.gilka.scubareddit.viewentry.ViewEntryActivity
import kotlinx.android.synthetic.main.fragment_listing.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class ListingFragment : BaseFragment(), ListingAdapter.OnItemClickListener {

    companion object {
        private const val TAG_LISTING = "redditListing"
    }

    private var redditListing: RedditListing? = null
    private val dataManager by lazy { RedditDataManager(getString(R.string.reddit_name)) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_listing, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // initAdapter
        if (rvListing.adapter == null) {
            rvListing.adapter = ListingAdapter(activity as Activity, this)
            rvListing.setHasFixedSize(true)
            val linearLayout = LinearLayoutManager(context)
            rvListing.layoutManager = linearLayout
            rvListing.addOnScrollListener(LoadMoreScrollListener({ this.getMoreEntries() }, linearLayout))

        }

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
        val entries = (rvListing.adapter as ListingAdapter).getCurrentEntries()
        if (redditListing != null && entries.isNotEmpty()) {
            outState.putParcelable(TAG_LISTING, redditListing?.copy(entries = entries))
        }
    }

    private fun getMoreEntries() {
        val subscription = dataManager
                .getListing(redditListing?.after ?: "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe (
                        { fetched ->
                            redditListing = fetched
                            (rvListing.adapter as ListingAdapter).loadMoreEntries(fetched.entries)
                        },
                        { e ->
                            if (view != null) {
                                Toast.makeText(activity!!, e.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                )
        subscriptions.add(subscription)
    }

    override fun onItemClick(item: AdapterViewBase) {
        if (item is RedditEntry) {
            val intent = Intent(activity, ViewEntryActivity::class.java)
            intent.putExtra(ViewEntryActivity.EXTRA_URL, item.url)
            startActivity(intent)
        }
    }

}
