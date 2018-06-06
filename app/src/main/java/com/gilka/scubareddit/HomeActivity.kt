package com.gilka.scubareddit

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.view.MenuItem
import com.gilka.scubareddit.favorites.FavoritesFragment
import com.gilka.scubareddit.listing.ListingFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var navigationBar: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        if (savedInstanceState == null) {
            showListing()
        }

        navigationBar = bottomNavigationView
        navigationBar.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (item.itemId == navigationBar.selectedItemId) {
            return false
        }

        when (item.itemId) {
            R.id.action_listing -> {
               showListing()
            }

            R.id.action_favorites -> {
               showFavorites()
            }
        }

        return true
    }

    private fun showListing() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, ListingFragment(), "listing")
                .commitNow()
        title = getString(R.string.tabs_listing)
    }

    private fun showFavorites() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, FavoritesFragment(), "favorites")
                .commitNow()
        title = getString(R.string.tabs_favorites)
    }

}