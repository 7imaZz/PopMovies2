package com.example.popmovies2.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.popmovies2.ui.MostPopularFragment
import com.example.popmovies2.ui.NowPlayingFragment
import com.example.popmovies2.ui.TopRatedFragment
import com.example.popmovies2.ui.UpcomingFragment

class SectionsPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity){


    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
            0-> return MostPopularFragment()
            1-> return NowPlayingFragment()
            2-> return UpcomingFragment()
            3-> return TopRatedFragment()
        }

        return MostPopularFragment()
    }

    fun getPageTitle(position: Int): String{
        when(position){
            0-> return "Most Popular"
            1-> return "Now Playing"
            2-> return "Upcoming"
            3-> return "Top Rated"
        }
        return "Most Popular"
    }

}