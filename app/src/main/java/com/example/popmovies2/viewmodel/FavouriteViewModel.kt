package com.example.popmovies2.viewmodel

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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


    fun getMoviesFromLocalDb(context: Context, progress: ProgressBar){
        getObservable(context).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<List<Result>> {
                override fun onSubscribe(d: Disposable) {
                    disposable.add(d)
                }

                override fun onNext(t: List<Result>) {
                    moviesLiveData.postValue(t)
                    //Log.d(TAG, "onNext: ${t.title}")
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "onError: "+e.message)
                }

                override fun onComplete() {
                    progress.visibility = View.GONE
                }

            })
    }

    private fun getObservable(context: Context): Observable<List<Result>>{
        return Observable.create(object : Observable<List<Result>>(),
            ObservableOnSubscribe<List<Result>> {
            override fun subscribe(emitter: ObservableEmitter<List<Result>>) {
                emitter.onNext(moviesClient.getMoviesFromLocalDb(context))
                emitter.onComplete()
            }

            override fun subscribeActual(observer: Observer<in List<Result>>?) {
            }

        })
    }
}