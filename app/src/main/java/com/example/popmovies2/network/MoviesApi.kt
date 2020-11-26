package com.example.popmovies2.network

import com.example.popmovies2.pojo.*
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

    @GET("movie/popular")
    fun getPopMovies(@Query("api_key") apiKey: String, @Query("page") page: Int)
            : Observable<Movies>

    @GET("movie/top_rated")
    fun getTopMovies(@Query("api_key") apiKey: String, @Query("page") page: Int)
            : Observable<Movies>

    @GET("movie/now_playing")
    fun getNowMovies(@Query("api_key") apiKey: String, @Query("page") page: Int)
            : Observable<Movies>

    @GET("movie/upcoming")
    fun getUpcomingMovies(@Query("api_key") apiKey: String, @Query("page") page: Int)
            : Observable<Movies>

    @GET("search/movie")
    fun search(@Query("api_key") apiKey: String, @Query("query") title: String)
            : Observable<Movies>

    @GET("movie/{movie_id}")
    fun getMovie(@Path("movie_id") movieId: Int, @Query("api_key") apiKey: String): Observable<MovieDetails>

    @GET("movie/{movie_id}/videos")
    fun getVideo(@Path("movie_id") movieId: Int, @Query("api_key") apiKey: String): Observable<Video>

    @GET("movie/{movie_id}/casts")
    fun getCasts(@Path("movie_id") movieId: Int, @Query("api_key") apiKey: String): Observable<MovieCast>

    @GET("movie/{movie_id}/similar")
    fun getSimilarMovies(@Path("movie_id") movieId: Int, @Query("api_key") apiKey: String): Observable<Movies>

    @GET("person/{person_id}/movie_credits")
    fun getPersonWork(@Path("person_id") movieId: Int, @Query("api_key") apiKey: String): Observable<Actor>

}