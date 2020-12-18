package com.example.popmovies2.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.popmovies2.R
import com.example.popmovies2.adapters.MoviesAdapter
import com.example.popmovies2.pojo.Result
import com.example.popmovies2.viewmodel.MovieViewModel

class TopRatedFragment : Fragment() {


    private var movieViewModel: MovieViewModel?= null
    private var adapter: MoviesAdapter?= null
    private var page = 1

    private var layoutManager: GridLayoutManager?= null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movies: MutableList<Result> = mutableListOf()

        adapter = MoviesAdapter(requireContext(), movies)


        layoutManager = GridLayoutManager(requireContext(), 2)

        val moviesRV = view.findViewById<RecyclerView>(R.id.movies_rv)
        val progressBar = view.findViewById<ProgressBar>(R.id.progress)

        moviesRV.setHasFixedSize(true)
        moviesRV.layoutManager = layoutManager
        moviesRV.itemAnimator = DefaultItemAnimator()
        moviesRV!!.adapter = adapter


        moviesRV.isNestedScrollingEnabled = false
        moviesRV.adapter = adapter

        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        movieViewModel!!.getTop("3b97af0112652688c49f023ecc57edb9", page, moviesRV, progressBar)

        getMovies()

    }

    @SuppressLint("SetTextI18n")
    private fun getMovies(){
        adapter!!.movies.clear()

        movieViewModel!!.moviesLiveData.observe(viewLifecycleOwner, Observer {
            adapter!!.movies.addAll(it)
            adapter!!.notifyDataSetChanged()
        })
    }

}