package com.example.popmovies2.viewmodel

import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.popmovies2.network.MoviesClient
import com.example.popmovies2.pojo.Movies
import com.example.popmovies2.pojo.Result
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class MovieViewModel: ViewModel() {

    private val TAG = "MovieViewModel"

    val moviesLiveData: MutableLiveData<MutableList<Result>> = MutableLiveData()
    private val moviesClient: MoviesClient = MoviesClient()
    var movies: MutableList<Result> = mutableListOf()

    private val compositeDisposable = CompositeDisposable()

    fun getMostPopular(apiKey: String, page: Int, progress: ProgressBar){
        compositeDisposable.add(
        moviesClient.getInstance().getPopMovies(apiKey, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<Movies>() {
                override fun onComplete() {
                    Log.d(TAG, "onComplete: ")
                    progress.visibility = View.GONE

                }

                override fun onNext(t: Movies) {
                    moviesLiveData.postValue(t.results as MutableList<Result>?)
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "onError: "+e.message)
                }

            })
        )
    }

    fun getTop(apiKey: String, page: Int, progress: ProgressBar){
        compositeDisposable.add(
            moviesClient.getInstance().getTopMovies(apiKey, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<Movies>() {
                    override fun onComplete() {
                        Log.d(TAG, "onComplete: ")
                        progress.visibility = View.GONE
                    }

                    override fun onNext(t: Movies) {
                        moviesLiveData.postValue(t.results as MutableList<Result>?)
                    }

                    override fun onError(e: Throwable) {
                        Log.e(TAG, "onError: "+e.message)
                    }

                })
        )
    }

    fun getNow(apiKey: String, page: Int, progress: ProgressBar){
        compositeDisposable.add(
            moviesClient.getInstance().getNowMovies(apiKey, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<Movies>() {
                    override fun onComplete() {
                        Log.d(TAG, "onComplete: ")
                        progress.visibility = View.GONE
                    }

                    override fun onNext(t: Movies) {
                        moviesLiveData.postValue(t.results as MutableList<Result>?)
                    }

                    override fun onError(e: Throwable) {
                        Log.e(TAG, "onError: "+e.message)
                    }

                })
        )
    }

    fun getUpcoming(apiKey: String, page: Int, progress: ProgressBar){
        compositeDisposable.add(
            moviesClient.getInstance().getUpcomingMovies(apiKey, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<Movies>() {
                    override fun onComplete() {
                        Log.d(TAG, "onComplete: ")
                        progress.visibility = View.GONE
                    }

                    override fun onNext(t: Movies) {
                        moviesLiveData.postValue(t.results as MutableList<Result>?)
                    }

                    override fun onError(e: Throwable) {
                        Log.e(TAG, "onError: "+e.message)
                    }

                })
        )
    }


    fun search(apiKey: String, title: String, progress: ProgressBar){
        compositeDisposable.add(
            moviesClient.getInstance().search(apiKey, title)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<Movies>() {
                    override fun onComplete() {
                        Log.d(TAG, "onComplete: ")
                        progress.visibility = View.GONE
                    }

                    override fun onNext(t: Movies) {
                        moviesLiveData.postValue(t.results as MutableList<Result>?)
                    }

                    override fun onError(e: Throwable) {
                        Log.e(TAG, "onError: "+e.message)
                        progress.visibility = View.GONE
                    }

                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}