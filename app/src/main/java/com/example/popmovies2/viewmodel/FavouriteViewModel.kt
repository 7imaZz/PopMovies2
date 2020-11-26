package com.example.popmovies2.viewmodel

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.popmovies2.adapters.MoviesAdapter
import com.example.popmovies2.network.MoviesClient
import com.example.popmovies2.pojo.Result
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class FavouriteViewModel : ViewModel() {

    private val TAG = "FavouriteViewModel"

    val moviesLiveData: MutableLiveData<List<Result>> = MutableLiveData()
    private val moviesClient: MoviesClient = MoviesClient()
    private val disposable = CompositeDisposable()
    var res = mutableListOf<Result>()


    fun getMoviesFromLocalDb(context: Context, progress: ProgressBar){
        getObservable(context).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Result> {
                override fun onSubscribe(d: Disposable) {
                    disposable.add(d)
                    res.clear()
                }

                override fun onNext(t: Result) {
                    res.add(t)
                    //Log.d(TAG, "onNext: ${t.title}")
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "onError: "+e.message)
                }

                override fun onComplete() {
                    moviesLiveData.postValue(res)
                    progress.visibility = View.GONE
                }

            })
    }

    private fun getObservable(context: Context): Observable<Result>{
        return Observable.create(object : Observable<Result>(),
            ObservableOnSubscribe<Result> {
            override fun subscribe(emitter: ObservableEmitter<Result>) {
                if (!emitter.isDisposed) {
                    moviesClient.getMoviesFromLocalDb(context).forEach{
                        emitter.onNext(it)
                    }
                }
                emitter.onComplete()
            }

            override fun subscribeActual(observer: Observer<in Result>?) {
            }

        })
    }
}