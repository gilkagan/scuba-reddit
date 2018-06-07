package com.gilka.scubareddit.favorites


import android.widget.Toast
import com.gilka.scubareddit.listing.BaseListingFragment
import com.gilka.scubareddit.data.DataManager
import com.gilka.scubareddit.listing.ListingAdapter
import kotlinx.android.synthetic.main.fragment_listing.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class FavoritesFragment : BaseListingFragment() {

    override fun getMoreEntries() {
        val subscription = DataManager.favoritesManager
                .favoritesObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { favorites ->
                            (rvListing.adapter as ListingAdapter).setInitialEntries(favorites)
                        },
                        { e ->
                            if (view != null) {
                                Toast.makeText(activity!!, e.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                )
        subscriptions.add(subscription)
    }

    override fun usePaging() = false
}
