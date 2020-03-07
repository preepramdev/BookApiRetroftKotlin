package com.pram.bookapiretrofit.api.manager

import android.content.Context
import com.pram.bookapiretrofit.api.service.BookApiService
import com.pram.bookapiretrofit.manager.Contextor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HttpManager private constructor() {
    private val mContext: Context?
    val service: BookApiService

    companion object {
        @JvmStatic
        var instance: HttpManager? = null
            get() {
                if (field == null) field = HttpManager()
                return field
            }
            private set
    }

    init {
        mContext = Contextor.instance!!.context

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

        val retrofit = Retrofit.Builder()
                .baseUrl("https://ancient-brook-04057.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

        service = retrofit.create(BookApiService::class.java)
    }
}