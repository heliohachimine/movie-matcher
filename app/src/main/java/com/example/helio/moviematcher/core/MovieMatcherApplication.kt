package com.example.helio.moviematcher.core

import android.app.Application
import com.example.helio.moviematcher.core.koin.appModule
import com.example.helio.moviematcher.core.koin.repositoryModule
import com.example.helio.moviematcher.core.koin.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MovieMatcherApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin {
            androidContext(this@MovieMatcherApplication)
            modules(
                listOf(
                    repositoryModule,
                    viewModelModule,
                    appModule
                )
            )
        }
    }

    companion object {
        @get:Synchronized
        var instance: MovieMatcherApplication? = null
            private set
    }
}
