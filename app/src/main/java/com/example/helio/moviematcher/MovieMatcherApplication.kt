package com.example.helio.moviematcher

import android.app.Application
import com.example.helio.moviematcher.core.appModule
import com.example.helio.moviematcher.core.repositoryModule
import com.example.helio.moviematcher.core.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class MovieMatcherApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this

        startKoin {
            androidContext(this@MovieMatcherApplication)
            modules(listOf(repositoryModule, viewModelModule, appModule))
        }

    }

//    fun setConnectivityListener(listener: ConnectivityReceiverListener?) {
//        ConnectivityReceiver.connectivityReceiverListener = listener
//    }

    companion object {
        @get:Synchronized

        var instance: MovieMatcherApplication? = null
            private set
    }
}
