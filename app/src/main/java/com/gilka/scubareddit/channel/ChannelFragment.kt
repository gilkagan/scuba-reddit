package com.gilka.scubareddit.channel


import android.widget.Toast
import com.gilka.scubareddit.listing.BaseListingFragment
import com.gilka.scubareddit.data.DataManager
import com.gilka.scubareddit.listing.ListingAdapter
import kotlinx.android.synthetic.main.fragment_listing.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class ChannelFragment : BaseListingFragment() {

    override fun getMoreEntries() {
        val subscription = DataManager.channelManager
                .getListing(redditListing?.after ?: "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
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

    override fun usePaging() = true
}
