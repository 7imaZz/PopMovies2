package com.example.popmovies2

import android.app.Application
import com.squareup.leakcanary.LeakCanary

class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()

        if (LeakCanary.isInAnalyzerProcess(this)){
            return
        }

        LeakCanary.install(this)
    }
}