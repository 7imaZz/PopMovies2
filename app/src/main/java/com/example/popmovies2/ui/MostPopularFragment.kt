package com.example.popmovies2.ui

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.popmovies2.R
import com.example.popmovies2.adapters.MoviesAdapter
import com.example.popmovies2.pojo.Result
import com.example.popmovies2.viewmodel.MovieViewModel
import de.hdodenhof.circleimageview.CircleImageView


class MostPopularFragment : Fragment() {

    private var pageNumTV : TextView ?= null
    var movieViewModel: MovieViewModel ?= null
    var adapter: MoviesAdapter ?= null
    var progressBar: ProgressBar ?= null
    var page = 1



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movies: MutableList<Result> = mutableListOf()

        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        adapter = MoviesAdapter(requireContext(), movies)


        val moviesRV = view.findViewById<RecyclerView>(R.id.movies_rv)
        val next = view.findViewById<CircleImageView>(R.id.next_btn)
        val prev = view.findViewById<CircleImageView>(R.id.prev_btn)
        progressBar = view.findViewById(R.id.progress)
        pageNumTV = view.findViewById(R.id.page_num_tv)

        next.visibility = View.VISIBLE
        prev.visibility = View.VISIBLE

        moviesRV.adapter = adapter

        getMovies()


        next.setOnClickListener {
            page++
            getMovies()
            progressBar!!.visibility = View.VISIBLE
        }

        prev.setOnClickListener{
            Log.d("Page: ", "$page")

            if (page>1){
                page--
                getMovies()
                progressBar!!.visibility = View.VISIBLE
            }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun getMovies(){
        adapter!!.movies.clear()

        movieViewModel!!.getMostPopular("3b97af0112652688c49f023ecc57edb9", page, progressBar!!)

        pageNumTV!!.text = "Page $page"

        movieViewModel!!.moviesLiveData.observe(viewLifecycleOwner, Observer {
            adapter!!.movies = it
            adapter!!.notifyDataSetChanged()
        })
    }

}