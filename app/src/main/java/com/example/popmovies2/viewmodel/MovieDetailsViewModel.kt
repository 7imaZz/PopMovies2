package com.example.popmovies2.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.popmovies2.network.MoviesClient
import com.example.popmovies2.pojo.*
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.CompletableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class MovieDetailsViewModel: ViewModel() {

    private val TAG = "MovieViewModel"

    val moviesLiveData: MutableLiveData<MovieDetails> = MutableLiveData()
    val videoLiveData: MutableLiveData<List<VideoResult>> = MutableLiveData()
    val castsLiveData: MutableLiveData<MovieCast> = MutableLiveData()
    val similarMoviesLiveData: MutableLiveData<Movies> = MutableLiveData()


    private val moviesClient: MoviesClient = MoviesClient()

    private val compositeDisposable = CompositeDisposable()

    fun getMovieDetails(apiKey: String, id: Int){
        compositeDisposable.add(moviesClient.getMovie(apiKey, id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<MovieDetails>(){
                override fun onComplete() {
                    Log.d(TAG, "onComplete")
                }

                override fun onNext(t: MovieDetails) {
                    moviesLiveData.postValue(t)
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "onError: "+e.message)
                }

            })
        )

    }

    fun getMovieTrailer(apiKey: String, id: Int){
        compositeDisposable.add(moviesClient.getVideo(apiKey, id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<Video>() {
                override fun onComplete() {
                    Log.d(TAG, "onComplete")
                }

                override fun onNext(t: Video) {
                    videoLiveData.postValue(t.results)
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "onError: "+e.message)
                }

            }))

    }

    fun getCasts(apiKey: String, id: Int){
        compositeDisposable.add(moviesClient.getCasts(apiKey, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<MovieCast>(){
                    override fun onComplete() {
                        Log.d(TAG, "onComplete")
                    }

                    override fun onNext(t: MovieCast) {
                        castsLiveData.postValue(t)
                    }

                    override fun onError(e: Throwable) {
                        Log.e(TAG, "onError: "+e.message)
                    }
                })
        )
    }

    fun getSimilarMovies(apiKey: String, id: Int){
        compositeDisposable.add(moviesClient.getSimilarMovies(apiKey, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<Movies>(){
                    override fun onComplete() {
                        Log.d(TAG, "onComplete")
                    }

                    override fun onNext(t: Movies) {
                        similarMoviesLiveData.postValue(t)
                    }

                    override fun onError(e: Throwable) {
                        Log.e(TAG, "onError: "+e.message)
                    }

                })
        )
    }

    fun insertMovie(context: Context, movie: Result){
        Completable.create {
            moviesClient.insertMovie(context, movie)
            it.onComplete()
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onComplete() {
                    Toast.makeText(context, "Added Successfully To Favourite", Toast.LENGTH_SHORT).show()
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "onError: " + e.message)
                }

            })
    }

    fun deleteMovie(context: Context, id: Int){
        Completable.create {
            moviesClient.deleteMovie(context, id)
            it.onComplete()
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onComplete() {
                    Toast.makeText(context, "Removed From Favourite", Toast.LENGTH_SHORT).show()
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "onError: " + e.message)
                }

            })
    }

    fun insertMovieInWatchList(context: Context, movie: Result){
        Completable.create {
            moviesClient.insertMovieIntoWatchlist(context, movie)
            it.onComplete()
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onComplete() {
                    Toast.makeText(context, "Added Successfully To Watchlist", Toast.LENGTH_SHORT).show()
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "onError: " + e.message)
                }

            })
    }

    fun deleteMovieFromWatchlist(context: Context, id: Int){
        Completable.create {
            moviesClient.deleteMovieFromWatchlist(context, id)
            it.onComplete()
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onComplete() {
                    Toast.makeText(context, "Removed From Watchlist", Toast.LENGTH_SHORT).show()
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "onError: " + e.message)
                }

            })
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}