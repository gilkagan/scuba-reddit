package com.gilka.scubareddit.favorites


import com.gilka.scubareddit.listing.BaseListingFragment
import com.gilka.scubareddit.data.DataManager
import com.gilka.scubareddit.listing.ListingAdapter
import kotlinx.android.synthetic.main.fragment_listing.*

class FavoritesFragment : BaseListingFragment() {

    override fun onResume() {
        super.onResume()
        getMoreEntries()
    }

    override fun getMoreEntries() {
        val favorites = DataManager.favoritesManager.favorites
        (rvListing.adapter as ListingAdapter).setInitialEntries(favorites)
    }

    override fun usePaging() = false
}
