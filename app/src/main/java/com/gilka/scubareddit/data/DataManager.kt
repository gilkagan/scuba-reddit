package com.gilka.scubareddit.data

object DataManager {

    val favoritesManager: FavoritesDataManager
    val channelManager : RedditDataManager

    init {
        favoritesManager = FavoritesDataManager()
        channelManager = RedditDataManager("scuba")
    }

}