package com.example.popmovies2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.popmovies2.R
import com.example.popmovies2.adapters.MoviesAdapter
import com.example.popmovies2.pojo.Result
import com.example.popmovies2.viewmodel.FavouriteViewModel

class FavouriteActivity : AppCompatActivity() {

    private val TAG = "FavouriteActivity"

    lateinit var progress: ProgressBar
    lateinit var moviesRV: RecyclerView

    lateinit var adapter: MoviesAdapter
    lateinit var favViewModel: FavouriteViewModel

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
        if (item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        //Log.d(TAG, "onStart")

        favViewModel.getMoviesFromLocalDb(this, progress)
        favViewModel.moviesLiveData.observe(this, Observer {
            adapter.movies = it as MutableList<Result>
            if (adapter.movies.isNotEmpty()){
                adapter.movies.reverse()
            }
            adapter.notifyDataSetChanged()
            supportActionBar!!.title = "Favourite(${it.size})"

        })
    }
}

