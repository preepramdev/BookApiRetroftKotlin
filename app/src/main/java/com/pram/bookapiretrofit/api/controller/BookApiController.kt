package com.pram.bookapiretrofit.api.controller

import com.pram.bookapiretrofit.api.callback.RetrofitCallback
import com.pram.bookapiretrofit.api.manager.HttpManager.Companion.instance
import com.pram.bookapiretrofit.api.service.BookApiService
import com.pram.bookapiretrofit.model.Book
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookApiController {
    var bookApiService: BookApiService

    init {
        bookApiService = instance!!.service
    }

    fun getBooks(callback: RetrofitCallback<List<Book?>?>) {
        val call = bookApiService.books
        call!!.enqueue(object : Callback<List<Book?>?> {
            override fun onResponse(call: Call<List<Book?>?>, response: Response<List<Book?>?>) {
                callback.onResponse(call, response)
            }

            override fun onFailure(call: Call<List<Book?>?>, t: Throwable) {
                callback.onFailure(call, t)
            }
        })
    }

    fun getBook(bookId: Int, callback: RetrofitCallback<Book?>) {
        val call = bookApiService.getBook(bookId)
        call!!.enqueue(object : Callback<Book?> {
            override fun onResponse(call: Call<Book?>, response: Response<Book?>) {
                callback.onResponse(call, response)
            }

            override fun onFailure(call: Call<Book?>, t: Throwable) {
                callback.onFailure(call, t)
            }
        })
    }

    fun createBook(book: Book?, callback: RetrofitCallback<Book?>) {
        val call = bookApiService.createBook(book)
        call!!.enqueue(object : Callback<Book?> {
            override fun onResponse(call: Call<Book?>, response: Response<Book?>) {
                callback.onResponse(call, response)
            }

            override fun onFailure(call: Call<Book?>, t: Throwable) {
                callback.onFailure(call, t)
            }
        })
    }

    fun updatePutBook(book: Book, callback: RetrofitCallback<Book?>) {
        val call = bookApiService.putBook(book.id, book)
        call!!.enqueue(object : Callback<Book?> {
            override fun onResponse(call: Call<Book?>, response: Response<Book?>) {
                callback.onResponse(call, response)
            }

            override fun onFailure(call: Call<Book?>, t: Throwable) {
                callback.onFailure(call, t)
            }
        })
    }

    fun updatePatchBook(book: Book, callback: RetrofitCallback<Book?>) {
        val call = bookApiService.patchBook(book.id, book)
        call!!.enqueue(object : Callback<Book?> {
            override fun onResponse(call: Call<Book?>, response: Response<Book?>) {
                callback.onResponse(call, response)
            }

            override fun onFailure(call: Call<Book?>, t: Throwable) {
                callback.onFailure(call, t)
            }
        })
    }

    fun removeBook(bookId: Int, callback: RetrofitCallback<Void?>) {
        val call = bookApiService.removeBook(bookId)
        call!!.enqueue(object : Callback<Void?> {
            override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                callback.onResponse(call, response)
            }

            override fun onFailure(call: Call<Void?>, t: Throwable) {
                callback.onFailure(call, t)
            }
        })
    }
}