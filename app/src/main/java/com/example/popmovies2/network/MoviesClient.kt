package com.example.popmovies2.network

import com.example.popmovies2.pojo.MovieDetails
import com.example.popmovies2.pojo.Movies
import com.example.popmovies2.pojo.Result
import com.example.popmovies2.pojo.Video
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MoviesClient() {

    private val baseUrl: String = "https://api.themoviedb.org/3/"


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

    fun search(apiKey: String, title: String): Call<Movies>{
        return moviesApi!!.search(apiKey, title)
    }

    fun getUpcomingMovies(apiKey: String, page: Int): Call<Movies>{
        return moviesApi!!.getUpcomingMovies(apiKey, page)
    }

    fun getMovie(apiKey: String, id: Int): Call<MovieDetails>{
        return moviesApi!!.getMovie(id, apiKey)
    }

    fun getVideo(apiKey: String, id: Int): Call<Video>{
        return moviesApi!!.getVideo(id, apiKey)
    }
}