package com.gilka.scubareddit.mvp.favorites.contracts

import com.gilka.scubareddit.mvp.listing.contracts.Model

interface Model {

    fun getFavoriteEntries(onFinishedListener: Model.OnGetEntriesFinishedListener)

}