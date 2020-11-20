package com.example.popmovies2.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.popmovies2.network.MoviesClient
import com.example.popmovies2.pojo.MovieDetails
import com.example.popmovies2.pojo.Video
import com.example.popmovies2.pojo.VideoResult
import retrofit2.Call
import retrofit2.Response

class MovieDetailsViewModel: ViewModel() {

    private val TAG = "MovieViewModel"

    val moviesLiveData: MutableLiveData<MovieDetails> = MutableLiveData()
    val videoLiveData: MutableLiveData<List<VideoResult>> = MutableLiveData()
    private val moviesClient: MoviesClient = MoviesClient()

    fun getMovieDetails(apiKey: String, id: Int){
        moviesClient.getMovie(apiKey, id).enqueue(object : retrofit2.Callback<MovieDetails>{

            override fun onFailure(call: Call<MovieDetails>, t: Throwable) {
                Log.d(TAG, "onFailure: "+t.message)

            }

            override fun onResponse(call: Call<MovieDetails>, response: Response<MovieDetails>) {
                moviesLiveData.postValue(response.body())
            }
        })
    }

    fun getMovieTrailer(apiKey: String, id: Int){
        moviesClient.getVideo(apiKey, id).enqueue(object : retrofit2.Callback<Video>{

            override fun onFailure(call: Call<Video>, t: Throwable) {
                Log.d(TAG, "onFailure: "+t.message)

            }

            override fun onResponse(call: Call<Video>, response: Response<Video>) {
                videoLiveData.postValue(response.body()!!.results)
            }
        })
    }
}