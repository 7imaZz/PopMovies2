package com.example.popmovies2.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.viewpager2.widget.ViewPager2
import com.example.popmovies2.R
import com.example.popmovies2.adapters.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null) {

            supportActionBar!!.elevation = 0F

            val mainTabLayout: TabLayout = findViewById(R.id.main_tab)
            val viewPager: ViewPager2 = findViewById(R.id.main_view_pager)
            val sectionsPagerAdapter = SectionsPagerAdapter(this)

            val tabLayoutMediator = TabLayoutMediator(mainTabLayout, viewPager){ tab: TabLayout.Tab, position: Int ->
                tab.text = sectionsPagerAdapter.getPageTitle(position)
                viewPager.currentItem = 0
            }

            viewPager.adapter = sectionsPagerAdapter
            tabLayoutMediator.attach()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        // Retrieve the SearchView and plug it into SearchManager
        // Retrieve the SearchView and plug it into SearchManager

        val menuItem: MenuItem = menu!!.findItem(R.id.search_menu)
        val searchView = menuItem.actionView as SearchView
        val searchManager =
            getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                searchView.setQuery("", false)
                val intent = Intent(this@MainActivity, SearchActivity::class.java)
                intent.putExtra("s", s)
                startActivity(intent)
                return true
            }

            override fun onQueryTextChange(s: String?): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.fav_menu -> {
                val intent = Intent(this@MainActivity, FavouriteActivity::class.java)
                startActivity(intent)
            }
            R.id.watchlist_menu -> {
                val intent = Intent(this@MainActivity, WatchlistActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}