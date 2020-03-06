package com.pram.bookapivolley

import android.app.Application
import com.pram.bookapivolley.api.controller.BookApiController
import com.pram.bookapivolley.manager.Contextor

val apiController: BookApiController by lazy {
    MainApplication.apiController!!
}

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        //Initialize thing(s) here
        Contextor.instance!!.init(applicationContext)
        apiController = BookApiController()
    }

    override fun onTerminate() {
        super.onTerminate()
    }

    companion object {
        var apiController: BookApiController? = null
    }
}