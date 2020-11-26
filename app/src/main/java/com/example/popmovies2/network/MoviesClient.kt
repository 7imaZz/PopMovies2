package com.example.popmovies2.network

import android.content.Context
import com.example.popmovies2.pojo.*
import com.example.popmovies2.room.MoviesDb
import com.example.popmovies2.room.WatchlistDb
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class MoviesClient {

    private val baseUrl: String = "https://api.themoviedb.org/3/"

    private var moviesApi: MoviesApi? = null
    private val instance: MoviesClient? = null

    init {
        moviesApi = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(MoviesApi::class.java)
    }

    fun getInstance(): MoviesClient{
        return instance ?: MoviesClient()
    }

    fun getPopMovies(apiKey: String, page: Int): Observable<Movies>{
        return moviesApi!!.getPopMovies(apiKey, page)
    }

    fun getTopMovies(apiKey: String, page: Int): Observable<Movies>{
        return moviesApi!!.getTopMovies(apiKey, page)
    }

    fun getNowMovies(apiKey: String, page: Int): Observable<Movies>{
        return moviesApi!!.getNowMovies(apiKey, page)
    }

    fun search(apiKey: String, title: String): Observable<Movies>{
        return moviesApi!!.search(apiKey, title)
    }

    fun getUpcomingMovies(apiKey: String, page: Int): Observable<Movies>{
        return moviesApi!!.getUpcomingMovies(apiKey, page)
    }

    fun getMovie(apiKey: String, id: Int): Observable<MovieDetails>{
        return moviesApi!!.getMovie(id, apiKey)
    }

    fun getVideo(apiKey: String, id: Int): Observable<Video>{
        return moviesApi!!.getVideo(id, apiKey)
    }

    fun getCasts(apiKey: String, id: Int): Observable<MovieCast>{
        return moviesApi!!.getCasts(id, apiKey)
    }

    fun getSimilarMovies(apiKey: String, id: Int): Observable<Movies>{
        return moviesApi!!.getSimilarMovies(id, apiKey)
    }

    fun getPersonWork(apiKey: String, id: Int): Observable<Actor>{
        return moviesApi!!.getPersonWork(id, apiKey)
    }

    fun getMoviesFromLocalDb(context: Context): List<Result>{
        return MoviesDb.getDatabase(context).movieDao()!!.getAllMovies()
    }

    fun insertMovie(context: Context, movie: Result){
        MoviesDb.getDatabase(context).movieDao()!!.insertMovie(movie)
    }

    fun deleteMovie(context: Context, id: Int){
        MoviesDb.getDatabase(context).movieDao()!!.deleteMovie(id)
    }

    fun getMoviesFromWatchlistDb(context: Context): List<Result>{
        return WatchlistDb.getDatabase(context).movieDao()!!.getAllMovies()
    }

    fun insertMovieIntoWatchlist(context: Context, movie: Result){
        WatchlistDb.getDatabase(context).movieDao()!!.insertMovie(movie)
    }

    fun deleteMovieFromWatchlist(context: Context, id: Int){
        WatchlistDb.getDatabase(context).movieDao()!!.deleteMovie(id)
    }
}