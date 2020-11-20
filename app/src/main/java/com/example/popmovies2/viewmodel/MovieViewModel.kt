package com.example.popmovies2.viewmodel

import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.popmovies2.network.MoviesClient
import com.example.popmovies2.pojo.Movies
import com.example.popmovies2.pojo.Result
import retrofit2.Call
import retrofit2.Response

class MovieViewModel: ViewModel() {

    private val TAG = "MovieViewModel"

    val moviesLiveData: MutableLiveData<MutableList<Result>> = MutableLiveData()
    private val moviesClient: MoviesClient = MoviesClient()
    var movies: MutableList<Result> = mutableListOf()

    fun getMostPopular(apiKey: String, page: Int, progress: ProgressBar){
        moviesClient.getInstance()!!.getPopMovies(apiKey, page).enqueue(object : retrofit2.Callback<Movies>{
            override fun onFailure(call: Call<Movies>, t: Throwable) {
                Log.d(TAG, "onFailure: "+t.message)
                progress.visibility = View.GONE
            }


            override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                movies = response.body()!!.results as MutableList<Result>
                moviesLiveData.postValue(movies)
                progress.visibility = View.GONE
            }
        })
    }

    fun getTop(apiKey: String, page: Int, progress: ProgressBar){
        moviesClient.getInstance()!!.getTopMovies(apiKey, page).enqueue(object : retrofit2.Callback<Movies>{
            override fun onFailure(call: Call<Movies>, t: Throwable) {
                Log.d(TAG, "onFailure: "+t.message)
                progress.visibility = View.GONE
            }


            override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                movies = response.body()!!.results as MutableList<Result>
                moviesLiveData.postValue(movies)
                progress.visibility = View.GONE
            }
        })
    }

    fun getNow(apiKey: String, page: Int, progress: ProgressBar){
        moviesClient.getInstance()!!.getNowMovies(apiKey, page).enqueue(object : retrofit2.Callback<Movies>{
            override fun onFailure(call: Call<Movies>, t: Throwable) {
                Log.d(TAG, "onFailure: "+t.message)
                progress.visibility = View.GONE
            }


            override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                movies = response.body()!!.results as MutableList<Result>
                moviesLiveData.postValue(movies)
                progress.visibility = View.GONE
            }
        })
    }

    fun getUpcoming(apiKey: String, page: Int, progress: ProgressBar){
        moviesClient.getInstance()!!.getUpcomingMovies(apiKey, page).enqueue(object : retrofit2.Callback<Movies>{
            override fun onFailure(call: Call<Movies>, t: Throwable) {
                Log.d(TAG, "onFailure: "+t.message)
                progress.visibility = View.GONE
            }


            override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                movies = response.body()!!.results as MutableList<Result>
                moviesLiveData.postValue(movies)
                progress.visibility = View.GONE
            }
        })
    }


    fun search(apiKey: String, title: String){
        moviesClient.getInstance()!!.search(apiKey, title).enqueue(object : retrofit2.Callback<Movies>{
            override fun onFailure(call: Call<Movies>, t: Throwable) {
                Log.d(TAG, "onFailure: "+t.message)
            }


            override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                movies = response.body()!!.results as MutableList<Result>
                moviesLiveData.postValue(movies)
            }
        })
    }

}