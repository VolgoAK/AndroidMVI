package com.attiladroid.androidmvi

import android.app.Application
import com.attiladroid.androidmvi.di.applicationModule
import com.attiladroid.androidmvi.presentation.di.presentationModule
import org.koin.android.ext.android.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, modules = listOf(applicationModule, presentationModule))

        Thread.setDefaultUncaughtExceptionHandler { thread, e ->
            println("Exception on thread ${thread.name}")
            e.printStackTrace()
            System.exit(1)
        }
    }
}