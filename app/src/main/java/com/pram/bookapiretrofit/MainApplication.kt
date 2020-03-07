package com.pram.bookapiretrofit

import android.app.Application
import com.pram.bookapiretrofit.api.controller.BookApiController
import com.pram.bookapiretrofit.manager.Contextor

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