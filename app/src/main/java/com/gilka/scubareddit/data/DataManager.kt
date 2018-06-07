package com.gilka.scubareddit.data

object DataManager {

    var favoritesManager: FavoritesDataManager

    init {
        favoritesManager = FavoritesDataManager()
    }

}