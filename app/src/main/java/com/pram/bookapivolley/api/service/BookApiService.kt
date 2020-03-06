package com.pram.bookapivolley.api.service

import com.pram.bookapivolley.model.Book
import retrofit2.Call
import retrofit2.http.*

interface BookApiService {
    @get:GET("books")
    val books: Call<List<Book?>?>?

    @GET("books/{id}")
    fun getBook(@Path("id") bookId: Int): Call<Book?>?

    @POST("books")
    fun createBook(@Body book: Book?): Call<Book?>?

    @PUT("books/{id}") // you must add all field
    fun putBook(@Path("id") bookId: Int, @Body book: Book?): Call<Book?>?

    @PATCH("books/{id}") // you can add just some field
    fun patchBook(@Path("id") bookId: Int, @Body book: Book?): Call<Book?>?

    @DELETE("books/{id}")
    fun removeBook(@Path("id") bookId: Int): Call<Void?>?
}