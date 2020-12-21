package com.example.popmovies2.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.popmovies2.R
import com.example.popmovies2.adapters.MoviesAdapter
import com.example.popmovies2.pojo.Result
import com.example.popmovies2.viewmodel.FavouriteViewModel
import java.util.*

class FavouriteActivity : AppCompatActivity() {

    private val TAG = "FavouriteActivity"

    lateinit var progress: ProgressBar
    lateinit var moviesRV: RecyclerView

    lateinit var adapter: MoviesAdapter
    lateinit var favViewModel: FavouriteViewModel

    var movies = mutableListOf<Result>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_movies)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        progress = findViewById(R.id.progress)
        moviesRV = findViewById(R.id.movies_rv)

        adapter = MoviesAdapter(this, mutableListOf())
        moviesRV.setHasFixedSize(true)
        moviesRV.adapter = adapter

        favViewModel = ViewModelProvider(this).get(FavouriteViewModel::class.java)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
            R.id.watchlist_menu -> {
                val intent = Intent(this@FavouriteActivity, WatchlistActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
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
                return true
            }


            override fun onQueryTextChange(s: String?): Boolean {
                val filteredMovies = mutableListOf<Result>()
                for (movie in movies) {
                    if (movie.title!!.toLowerCase(Locale.ROOT).contains(s!!.toLowerCase(Locale.ROOT))) {
                        filteredMovies.add(movie)
                    }
                }

                adapter.movies = filteredMovies
                adapter.notifyDataSetChanged()

                return true
            }
        })
        return true
    }

    override fun onStart() {
        super.onStart()
        //Log.d(TAG, "onStart")

        favViewModel.getMoviesFromLocalDb(this, progress)

        favViewModel.moviesLiveData.observe(this, Observer {
            movies = it as MutableList<Result>
            adapter.movies = movies
            adapter.notifyDataSetChanged()
            supportActionBar!!.title = "Favourite(${it.size})"
        })
    }
}

