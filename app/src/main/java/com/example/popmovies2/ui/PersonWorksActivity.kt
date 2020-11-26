package com.example.popmovies2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.popmovies2.R
import com.example.popmovies2.adapters.MoviesAdapter
import com.example.popmovies2.pojo.Cast
import com.example.popmovies2.pojo.Crew
import com.example.popmovies2.pojo.Result
import com.example.popmovies2.viewmodel.PersonWorksViewModel

class PersonWorksActivity : AppCompatActivity() {

    lateinit var actorWorksRV: RecyclerView
    lateinit var progress: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_movies)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val bundle: Bundle? = intent.getBundleExtra("b")
        val isDir: Boolean = intent.getBooleanExtra("dir", false)
        val castRes: Cast? = bundle!!.getParcelable("cast")
        val crewRes: Crew? = bundle.getParcelable("crew")

        actorWorksRV = findViewById(R.id.movies_rv)
        progress = findViewById(R.id.progress)

        val adapter = MoviesAdapter(this, mutableListOf())

        actorWorksRV.adapter = adapter

        val personWorksViewModel: PersonWorksViewModel = ViewModelProvider(this).get( PersonWorksViewModel::class.java)

        if (isDir){
            personWorksViewModel.getDirWorks("3b97af0112652688c49f023ecc57edb9", crewRes!!.id!!, progress)
            supportActionBar!!.title = crewRes.name+" Movies"
        }else {
            personWorksViewModel.getActorWorks("3b97af0112652688c49f023ecc57edb9", castRes!!.id!!, progress)
            supportActionBar!!.title = castRes.name+" Movies"
        }

        personWorksViewModel.worksLiveData.observe(this, Observer {
            if (!isDir) {
                adapter.movies = it.works as MutableList<Result>
                adapter.notifyDataSetChanged()
            }else{
                adapter.movies = it.crew as MutableList<Result>
                adapter.notifyDataSetChanged()
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}