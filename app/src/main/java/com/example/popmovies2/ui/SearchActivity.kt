package com.example.popmovies2.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.popmovies2.R
import com.example.popmovies2.adapters.MoviesAdapter
import com.example.popmovies2.viewmodel.MovieViewModel

class SearchActivity : AppCompatActivity() {

    var progressBar: ProgressBar?= null

    var movieViewModel: MovieViewModel ?= null
    var adapter: MoviesAdapter?= null
    var query = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_movies)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        adapter = MoviesAdapter(this, mutableListOf())

        val moviesRV = findViewById<RecyclerView>(R.id.movies_rv)
        progressBar = findViewById(R.id.progress)

        query = intent.getStringExtra("s").toString()
        getMovies(query)

        moviesRV.setHasFixedSize(true)
        moviesRV.isNestedScrollingEnabled = false
        moviesRV.adapter = adapter
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

        searchView.setQuery(query,false)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {

                getMovies(s)
                return true
            }

            override fun onQueryTextChange(s: String?): Boolean {
                return false
            }
        })
        return true
    }

    private fun getMovies(s: String){
        adapter!!.movies.clear()
        adapter!!.notifyDataSetChanged()

        progressBar!!.visibility = View.VISIBLE
        movieViewModel!!.search("3b97af0112652688c49f023ecc57edb9", s, progressBar!!)

        movieViewModel!!.moviesLiveData.observe(this@SearchActivity, Observer {
            if (it.isNotEmpty()){
                adapter!!.movies = it
                adapter!!.notifyDataSetChanged()
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            android.R.id.home ->{
                finish()
            }
            R.id.fav_menu -> {
                val intent = Intent(this@SearchActivity, FavouriteActivity::class.java)
                startActivity(intent)
            }
            R.id.watchlist_menu -> {
                val intent = Intent(this@SearchActivity, WatchlistActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}