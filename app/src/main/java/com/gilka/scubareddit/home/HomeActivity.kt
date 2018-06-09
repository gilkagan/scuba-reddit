package com.gilka.scubareddit.home

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.gilka.scubareddit.R
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var navigationBar: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        if (savedInstanceState == null) {
            showListing()
        }

        vpContainer.adapter = ViewPagerAdapter(supportFragmentManager)
        vpContainer.offscreenPageLimit = 3

        navigationBar = bottomNavigationView
        navigationBar.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (item.itemId == navigationBar.selectedItemId) {
            return false
        }

        when (item.itemId) {
            R.id.action_listing -> showListing()
            R.id.action_favorites -> showFavorites()
        }

        return true
    }

    private fun showListing() {
        vpContainer.currentItem = 0
    }

    private fun showFavorites() {
        vpContainer.currentItem = 1
    }

}