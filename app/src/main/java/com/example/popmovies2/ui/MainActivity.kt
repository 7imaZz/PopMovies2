package com.example.popmovies2.ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.viewpager2.widget.ViewPager2
import com.example.popmovies2.R
import com.example.popmovies2.adapters.SectionsPagerAdapter
import com.example.popmovies2.viewmodel.MovieViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy


class MainActivity : AppCompatActivity() {

    var movieViewModel: MovieViewModel ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar!!.elevation = 0F

        val mainTabLayout: TabLayout = findViewById(R.id.main_tab)
        val viewPager: ViewPager2 = findViewById(R.id.main_view_pager)
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)


        val tabLayoutMediator = TabLayoutMediator(mainTabLayout, viewPager,
            TabConfigurationStrategy { tab: TabLayout.Tab, position: Int ->
                tab.text = sectionsPagerAdapter.getPageTitle(position)
                viewPager.currentItem = 0
            }
        )

        viewPager.adapter = sectionsPagerAdapter
        tabLayoutMediator.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        // Retrieve the SearchView and plug it into SearchManager
        // Retrieve the SearchView and plug it into SearchManager
        val searchView = MenuItemCompat.getActionView(menu!!.findItem(R.id.search_menu)) as SearchView
        val searchManager =
            getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {

                movieViewModel!!.search("3b97af0112652688c49f023ecc57edb9", s)

                movieViewModel!!.moviesLiveData.observe(this@MainActivity, Observer {
                    if (it.isNotEmpty()){
                        Toast.makeText(this@MainActivity, it[0].title, Toast.LENGTH_SHORT).show()
                    }
                })
                return true
            }

            override fun onQueryTextChange(s: String?): Boolean {
                return false
            }
        })
        return true
    }
}