package com.example.popmovies2.network

import com.example.popmovies2.pojo.Movies
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {

    @GET("movie/popular")
    fun getPopMovies(@Query("api_key") apiKey: String, @Query("page") page: Int)
            : Call<Movies>

    @GET("movie/top_rated")
    fun getTopMovies(@Query("api_key") apiKey: String, @Query("page") page: Int)
            : Call<Movies>

    @GET("movie/now_playing")
    fun getNowMovies(@Query("api_key") apiKey: String, @Query("page") page: Int)
            : Call<Movies>

    @GET("movie/upcoming")
    fun getUpcomingMovies(@Query("api_key") apiKey: String, @Query("page") page: Int)
            : Call<Movies>
}