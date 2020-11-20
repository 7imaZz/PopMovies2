package com.example.popmovies2.network

import com.example.popmovies2.pojo.Movies
import com.example.popmovies2.pojo.Result
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MoviesClient() {

    val baseUrl: String = "https://api.themoviedb.org/3/"


    private var moviesApi: MoviesApi? = null
    private val instance: MoviesClient? = null

    init {
        moviesApi = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MoviesApi::class.java)
    }

    fun getInstance(): MoviesClient? {
        return instance ?: MoviesClient()
    }

    fun getPopMovies(apiKey: String, page: Int): Call<Movies>{
        return moviesApi!!.getPopMovies(apiKey, page)
    }

    fun getTopMovies(apiKey: String, page: Int): Call<Movies>{
        return moviesApi!!.getTopMovies(apiKey, page)
    }

    fun getNowMovies(apiKey: String, page: Int): Call<Movies>{
        return moviesApi!!.getNowMovies(apiKey, page)
    }

    fun getUpcomingMovies(apiKey: String, page: Int): Call<Movies>{
        return moviesApi!!.getUpcomingMovies(apiKey, page)
    }
}