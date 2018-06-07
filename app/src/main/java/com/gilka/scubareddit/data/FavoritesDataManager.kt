package com.gilka.scubareddit.data

import com.gilka.scubareddit.models.RedditEntry
import java.util.ArrayList

class FavoritesDataManager {

    var favorites: ArrayList<RedditEntry> = ArrayList()

    fun toggleFavorite(entry: RedditEntry) {
        if (checkFavorite(entry)) {
            favorites.remove(entry)
        } else {
            favorites.add(entry)
        }
    }

    fun checkFavorite(post: RedditEntry): Boolean {
        return favorites.contains(post)
    }

}