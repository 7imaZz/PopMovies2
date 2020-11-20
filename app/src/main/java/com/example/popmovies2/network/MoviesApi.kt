package com.example.popmovies2.network

import com.example.popmovies2.pojo.MovieDetails
import com.example.popmovies2.pojo.Movies
import com.example.popmovies2.pojo.Video
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
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

    @GET("search/movie")
    fun search(@Query("api_key") apiKey: String, @Query("query") title: String)
            : Call<Movies>

    @GET("movie/{movie_id}")
    fun getMovie(@Path("movie_id") movieId: Int, @Query("api_key") apiKey: String): Call<MovieDetails>

    @GET("movie/{movie_id}/videos")
    fun getVideo(@Path("movie_id") movieId: Int, @Query("api_key") apiKey: String): Call<Video>


}