package com.gilka.scubareddit.data

import com.gilka.scubareddit.models.RedditEntry
import com.gilka.scubareddit.models.RedditListing
import rx.Observable
import rx.subjects.PublishSubject
import rx.subjects.ReplaySubject
import java.util.ArrayList

class FavoritesDataManager {

    private var favorites: ArrayList<RedditEntry> = ArrayList()
    val favoritesObservable = ReplaySubject.create<List<RedditEntry>>()

    init {
        publish()
    }

    private fun publish() {
        favoritesObservable.onNext(favorites)
    }

    fun toggleFavorite(entry: RedditEntry) {
        if (checkFavorite(entry)) {
            favorites.remove(entry)
        } else {
            favorites.add(entry)
        }
        publish()
    }

    fun checkFavorite(post: RedditEntry): Boolean {
        return favorites.contains(post)
    }
}