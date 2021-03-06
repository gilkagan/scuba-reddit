package com.gilka.scubareddit.home

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.gilka.scubareddit.favorites.FavoritesFragment
import com.gilka.scubareddit.channel.ChannelFragment

class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return ChannelFragment()
            1 -> return FavoritesFragment()
        }
        return ChannelFragment()
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return "Channel"
            1 -> return "Favorites"
        }
        return ""
    }
}
