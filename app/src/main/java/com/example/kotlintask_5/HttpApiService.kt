package com.example.kotlintask_5


import com.example.kotlintask_5.model.BookListItem
import retrofit2.http.GET


interface HttpApiService {

    @GET("/books")
    suspend fun getAllBooks(): List<BookListItem>


}