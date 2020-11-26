package com.example.popmovies2.viewmodel

import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.popmovies2.network.MoviesClient
import com.example.popmovies2.pojo.Actor
import com.example.popmovies2.pojo.Result
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Function
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class PersonWorksViewModel: ViewModel() {

    private val TAG = "MovieViewModel"

    val worksLiveData: MutableLiveData<Actor> = MutableLiveData()

    private val moviesClient: MoviesClient = MoviesClient()

    private val compositeDisposable = CompositeDisposable()

    fun getActorWorks(apiKey: String, id: Int, progress: ProgressBar){
        compositeDisposable.add(moviesClient.getPersonWork(apiKey, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<Actor>() {
                    override fun onComplete() {
                        Log.d(TAG, "onComplete: Actor")
                        progress.visibility = View.GONE
                    }

                    override fun onNext(t: Actor) {
                        worksLiveData.postValue(t)
                    }

                    override fun onError(e: Throwable) {
                        Log.e(TAG, "onError: "+e.message)
                    }
                }))
    }

    fun getDirWorks(apiKey: String, id: Int, progress: ProgressBar){
        compositeDisposable.add(moviesClient.getPersonWork(apiKey, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(object : Function<Actor, Actor> {
                    var res: MutableList<Result> = mutableListOf()
                    override fun apply(t: Actor): Actor {
                        t.crew!!.forEach {
                            if (it.job.equals("Director")){
                                Log.d(TAG, "apply: director: "+it.title+it.job)
                                res.add(it)
                            }
                        }
                        t.crew = res
                        return t
                    }
                })
                .subscribeWith(object : DisposableObserver<Actor>() {
                    override fun onComplete() {
                        Log.d(TAG, "onComplete: Actor")
                        progress.visibility = View.GONE
                    }

                    override fun onNext(t: Actor) {
                        worksLiveData.postValue(t)
                    }

                    override fun onError(e: Throwable) {
                        Log.e(TAG, "onError: " + e.message)
                    }
                }))
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}