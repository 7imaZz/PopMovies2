package com.example.popmovies2.viewmodel

import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.example.popmovies2.network.MoviesClient
import com.example.popmovies2.pojo.Movies
import com.example.popmovies2.pojo.Result
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers


open class MovieViewModel: ViewModel() {

    private val TAG = "MovieViewModel"

    val moviesLiveData: MutableLiveData<MutableList<Result>> = MutableLiveData()
    private val moviesClient: MoviesClient = MoviesClient()

    private val compositeDisposable = CompositeDisposable()

    fun getMostPopular(apiKey: String, page: Int, recyclerView: RecyclerView, progress: ProgressBar){

        var pageT = page
        var totalPages = 2511
        var mIsLoading = false



        recyclerView.addOnScrollListener(object : OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE
                    && !mIsLoading && pageT<totalPages){
                    progress.visibility = View.VISIBLE
                    Log.d(TAG, "onScrollStateChanged: Bottom$pageT:$totalPages")
                    pageT++
                    getMostPopular("3b97af0112652688c49f023ecc57edb9", pageT, recyclerView, progress)
                    mIsLoading = true
                }
            }
        })

        compositeDisposable.add(
            moviesClient.getInstance().getPopMovies(apiKey, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<Movies>() {
                    override fun onComplete() {
                        Log.d(TAG, "Pop onComplete: $pageT")
                        progress.visibility = View.GONE
                        mIsLoading = false
                    }

                    override fun onNext(t: Movies) {
                        moviesLiveData.postValue(t.results as MutableList<Result>?)
                        Log.d(TAG, "onNext: ${t.results!![0].title}")
                        totalPages = t.total_pages!!
                    }

                    override fun onError(e: Throwable) {
                        Log.e(TAG, "onError: " + e.message)
                    }

                })
        )
    }

    fun getTop(apiKey: String, page: Int, recyclerView: RecyclerView, progress: ProgressBar){

        var pageT = page
        var totalPages = 2511
        var mIsLoading = false


        recyclerView.addOnScrollListener(object : OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE
                    && !mIsLoading && pageT<totalPages){
                    progress.visibility = View.VISIBLE
                    Log.d(TAG, "onScrollStateChanged: Bottom$pageT:$totalPages")
                    pageT++
                    getTop("3b97af0112652688c49f023ecc57edb9", pageT, recyclerView, progress)
                    mIsLoading = true
                }
            }
        })
        compositeDisposable.add(
            moviesClient.getInstance().getTopMovies(apiKey, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<Movies>() {
                    override fun onComplete() {
                        Log.d(TAG, "Top onComplete: $pageT")
                        progress.visibility = View.GONE
                        mIsLoading = false
                    }

                    override fun onNext(t: Movies) {
                        moviesLiveData.postValue(t.results as MutableList<Result>?)
                        totalPages = t.total_pages!!
                    }

                    override fun onError(e: Throwable) {
                        Log.e(TAG, "onError: " + e.message)
                    }

                })
        )
    }

    fun getNow(apiKey: String, page: Int, recyclerView: RecyclerView, progress: ProgressBar){

        var pageT = page
        var totalPages = 2511
        var mIsLoading = false


        recyclerView.addOnScrollListener(object : OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE
                    && !mIsLoading && pageT<totalPages){
                    progress.visibility = View.VISIBLE
                    Log.d(TAG, "onScrollStateChanged: Bottom$pageT:$totalPages")
                    pageT++
                    getNow("3b97af0112652688c49f023ecc57edb9", pageT, recyclerView, progress)
                    mIsLoading = true
                }
            }
        })

        compositeDisposable.add(
            moviesClient.getInstance().getNowMovies(apiKey, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<Movies>() {
                    override fun onComplete() {
                        Log.d(TAG, "Now onComplete: $pageT")
                        progress.visibility = View.GONE
                        mIsLoading = false
                    }

                    override fun onNext(t: Movies) {
                        moviesLiveData.postValue(t.results as MutableList<Result>?)
                        totalPages = t.total_pages!!
                    }

                    override fun onError(e: Throwable) {
                        Log.e(TAG, "onError: " + e.message)
                    }

                })
        )
    }

    fun getUpcoming(apiKey: String, page: Int, recyclerView: RecyclerView, progress: ProgressBar){

        var pageT = page
        var totalPages = 2511
        var mIsLoading = false


        recyclerView.addOnScrollListener(object : OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE
                    && !mIsLoading && pageT<totalPages){
                    progress.visibility = View.VISIBLE
                    Log.d(TAG, "onScrollStateChanged: Bottom$pageT:$totalPages")
                    pageT++
                    getUpcoming("3b97af0112652688c49f023ecc57edb9", pageT, recyclerView, progress)
                    mIsLoading = true
                }
            }
        })

        compositeDisposable.add(
            moviesClient.getInstance().getUpcomingMovies(apiKey, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<Movies>() {
                    override fun onComplete() {
                        Log.d(TAG, "Up onComplete: $pageT")
                        progress.visibility = View.GONE
                        mIsLoading = false
                    }

                    override fun onNext(t: Movies) {
                        moviesLiveData.postValue(t.results as MutableList<Result>?)
                        totalPages = t.total_pages!!
                    }

                    override fun onError(e: Throwable) {
                        Log.e(TAG, "onError: " + e.message)
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
                        Log.e(TAG, "onError: " + e.message)
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