package com.pram.bookapiretrofit.manager

import android.content.Context

/**
 * Created by nuuneoi on 12/6/14 AD.
 */
class Contextor private constructor() {
    var context: Context? = null

    fun init(context: Context?) {
        this.context = context
    }

    companion object {
        @JvmStatic
        var instance: Contextor? = null
            get() {
                if (field == null) field = Contextor()
                return field
            }
    }
}