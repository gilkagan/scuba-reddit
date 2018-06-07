package com.gilka.scubareddit.home

import android.app.SearchManager
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.widget.SearchView
import android.view.Menu
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

        navigationBar = bottomNavigationView
        navigationBar.setOnNavigationItemSelectedListener(this)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.action_filter).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        searchView.isFocusable = true
        searchView.isSubmitButtonEnabled = true
        searchView.maxWidth = Integer.MAX_VALUE

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                return true
            }

            override fun onQueryTextChange(query: String): Boolean {

                return false
            }
        })
        return true
    }

    private fun showListing() {
        vpContainer.currentItem = 0
    }

    private fun showFavorites() {
        vpContainer.currentItem = 1
    }

}