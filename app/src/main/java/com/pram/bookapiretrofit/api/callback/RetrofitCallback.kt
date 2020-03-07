package com.pram.bookapiretrofit.api.callback

import retrofit2.Call
import retrofit2.Response

/**
 * Custom CallBack From retrofit2 CallBack
 */
interface RetrofitCallback<T> {
    fun onResponse(call: Call<T>?, response: Response<T>?)
    fun onFailure(call: Call<T>?, t: Throwable?)
}